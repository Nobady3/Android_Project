package com.example.myapplication.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.example.myapplication.db.CustomerDbHelper;

public class AddCustomerActivity extends AppCompatActivity {

    private EditText etAccountNumber, etPassword, etFullName, etIdCard, etEmail, etAddress, etPhoneNumber;
    private Button btnSave;
    private CustomerDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer); // 设置布局文件

        // 初始化控件
        etAccountNumber = findViewById(R.id.etAccountNumber);
        etPassword = findViewById(R.id.etPassword);
        etFullName = findViewById(R.id.etFullName);
        etIdCard = findViewById(R.id.etIdCard);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnSave = findViewById(R.id.btnSave);

        //返回
        findViewById(R.id.customer_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 初始化数据库助手
        dbHelper = new CustomerDbHelper(this);

        // 设置按钮点击事件
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取用户输入的数据
                String accountNumber = etAccountNumber.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String fullName = etFullName.getText().toString().trim();
                String idCard = etIdCard.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                String phoneNumber = etPhoneNumber.getText().toString().trim();

                // 验证输入内容
                if (TextUtils.isEmpty(accountNumber) || TextUtils.isEmpty(password) ||
                        TextUtils.isEmpty(fullName) || TextUtils.isEmpty(idCard) ||
                        TextUtils.isEmpty(email) || TextUtils.isEmpty(address) ||
                        TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(AddCustomerActivity.this, "所有字段都必须填写", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 将用户输入的信息添加到数据库
                long result = dbHelper.addCustomerInfo(accountNumber, password, fullName, idCard, phoneNumber, email, address);

                // 检查是否成功添加
                if (result == -1) {
                    Toast.makeText(AddCustomerActivity.this, "添加失败，请重试", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddCustomerActivity.this, "客户添加成功", Toast.LENGTH_SHORT).show();
                    // 返回上一个界面或进行后续操作
                    setResult(Activity.RESULT_OK); // 设置返回成功的结果
                    finish();
                }



            }

        });
    }
}
