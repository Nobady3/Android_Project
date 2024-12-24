package com.example.myapplication.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.db.UserDbHelper;
import com.example.myapplication.entity.UserInfo;

public class RegisterActivity extends AppCompatActivity {
    private EditText et_username;
    private EditText et_password;
    private EditText et_tel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //初始化控件
        et_username=findViewById(R.id.et_username);
        et_password=findViewById(R.id.et_password);
        et_tel=findViewById(R.id.et_tel);

        //返回
        findViewById(R.id.toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        //点击注册
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                String tel = et_tel.getText().toString();

                // 检查用户名
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                } else if (username.length() > 5) {
                    Toast.makeText(RegisterActivity.this, "用户名不能超过五位", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 检查密码
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.length() < 6 || password.length() > 10) {
                    Toast.makeText(RegisterActivity.this, "密码长度必须在6到10位之间", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 检查电话
                if (TextUtils.isEmpty(tel)) {
                    Toast.makeText(RegisterActivity.this, "请输入电话", Toast.LENGTH_SHORT).show();
                    return;
                } else if (tel.length() != 11) {
                    Toast.makeText(RegisterActivity.this, "电话必须为11位", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 检查用户名是否已存在
                if (UserDbHelper.getInstance(RegisterActivity.this).isUsernameExist(username)) {
                    Toast.makeText(RegisterActivity.this, "用户名已存在，请选择其他用户名", Toast.LENGTH_SHORT).show();
                } else {
                    // 注册新用户
                    int row = UserDbHelper.getInstance(RegisterActivity.this).register(username, password, tel);
                    if (row > 0) {
                        Toast.makeText(RegisterActivity.this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "注册失败，请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });




    }
}