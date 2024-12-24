package com.example.myapplication.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.db.UserDbHelper;
import com.example.myapplication.entity.UserInfo;

public class UpdateInfoActivity extends AppCompatActivity {
    private EditText et_new_username;
    private EditText et_new_tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        //初始化控件
        et_new_username=findViewById(R.id.et_new_username);
        et_new_tel=findViewById(R.id.et_new_tel);
        UserInfo userInfo=UserInfo.getUserInfo();

        if(null!=userInfo){
            String username = userInfo.getUsername();
            String tel = userInfo.getTel();
            et_new_username.setText(username);
            et_new_tel.setText(tel);
        }


        findViewById(R.id.btn_update_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_username=et_new_username.getText().toString();
                String new_tel=et_new_tel.getText().toString();
                if(TextUtils.isEmpty(new_username)||TextUtils.isEmpty(new_tel)){
                    Toast.makeText(UpdateInfoActivity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
                } else if (new_username.length() > 5) {
                    Toast.makeText(UpdateInfoActivity.this, "用户名不能超过五位", Toast.LENGTH_SHORT).show();
                } else if(new_tel.length()!=11){
                    Toast.makeText(UpdateInfoActivity.this, "电话必须为11位", Toast.LENGTH_SHORT).show();
                }else{
                    if(null!=userInfo){
                        int row = UserDbHelper.getInstance(UpdateInfoActivity.this).updateInfo(userInfo.getUsername(), new_username,new_tel);
                        if(row>0){
                            Toast.makeText(UpdateInfoActivity.this, "修改信息成功!", Toast.LENGTH_SHORT).show();
                            userInfo.setUsername(new_username);
                            userInfo.setTel(new_tel);
                            UserInfo.setUserInfo(userInfo);
                            setResult(2000);
                            finish();
                        }else{
                            Toast.makeText(UpdateInfoActivity.this, "修改失败!", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }
        });


        //返回
        findViewById(R.id.toolbar_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}