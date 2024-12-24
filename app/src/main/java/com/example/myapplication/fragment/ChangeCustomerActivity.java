package com.example.myapplication.fragment;

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
import com.example.myapplication.entity.CustomerInfo;

public class ChangeCustomerActivity extends AppCompatActivity {

    private EditText etAccountNumber, etPassword, etEmail, etAddress, etPhoneNumber;
    private Button btnSave;
    private CustomerDbHelper customerDbHelper;
    private int customerId; // 当前客户的 ID（用于更新数据库中的数据）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_customer); // 设置布局

        // 初始化UI元素
        etAccountNumber = findViewById(R.id.etAccountNumber);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnSave = findViewById(R.id.btnSave);

        // 初始化数据库帮助类
        customerDbHelper = CustomerDbHelper.getInstance(this);

        // 获取传递过来的客户 ID
        customerId = getIntent().getIntExtra("customer_id", -1); // 获取 customer_id
        if (customerId != -1) {
            loadCustomerData();  // 加载客户数据并显示在输入框中
        }

        findViewById(R.id.customer_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 保存按钮点击事件
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取用户输入的数据
                String accountNumber = etAccountNumber.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                String phoneNumber = etPhoneNumber.getText().toString().trim();

                // 验证输入内容
                if (TextUtils.isEmpty(accountNumber) || TextUtils.isEmpty(password) ||
                        TextUtils.isEmpty(email) || TextUtils.isEmpty(address) ||
                        TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(ChangeCustomerActivity.this, "所有字段都必须填写", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 更新客户信息
                boolean isUpdated = updateCustomerInfo(accountNumber, password, email, address, phoneNumber);
                if (isUpdated) {
                    Toast.makeText(ChangeCustomerActivity.this, "客户信息更新成功", Toast.LENGTH_SHORT).show();
                    // 返回主界面，通知列表刷新
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(ChangeCustomerActivity.this, "更新失败，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 加载现有客户数据并填充到输入框中
     */
    private void loadCustomerData() {
        // 获取客户信息
        CustomerInfo customerInfo = customerDbHelper.getCustomerInfo(customerId);
        if (customerInfo != null) {
            // 填充到输入框中
            etAccountNumber.setText(customerInfo.getAccountNumber());
            etPassword.setText(customerInfo.getPassword());
            etEmail.setText(customerInfo.getEmail());
            etAddress.setText(customerInfo.getAddress());
            etPhoneNumber.setText(customerInfo.getPhoneNumber());
        }
    }

    /**
     * 更新客户信息到数据库
     * @param accountNumber 账号
     * @param password 密码
     * @param email 邮箱
     * @param address 地址
     * @param phoneNumber 电话号码
     * @return 更新是否成功
     */
    private boolean updateCustomerInfo(String accountNumber, String password, String email, String address, String phoneNumber) {
        // 调用数据库帮助类更新信息
        int rowsAffected = customerDbHelper.updateCustomerInfo(customerId, accountNumber, password, email, address, phoneNumber);
        return rowsAffected > 0;
    }

}
