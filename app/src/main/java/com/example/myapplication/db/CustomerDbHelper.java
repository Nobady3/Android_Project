package com.example.myapplication.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapplication.entity.CustomerInfo;

import java.util.ArrayList;
import java.util.List;

public class CustomerDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "customer.db";  // 数据库名
    private static final int VERSION = 3;             // 版本号

    private static CustomerDbHelper sInstance;

    // 单例模式
    public static synchronized CustomerDbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new CustomerDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    // 构造方法
    public CustomerDbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建 customer_info 表
        db.execSQL("CREATE TABLE customer_info (" +
                "customer_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "account_number TEXT NOT NULL, " +
                "password TEXT NOT NULL, " +
                "full_name TEXT NOT NULL, " +
                "id_card TEXT NOT NULL, " +
                "phone_number TEXT, " +
                "email TEXT, " +
                "address TEXT" +
                ")");
        Log.d("CustomerDbHelper", "customer_info table created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            // 如果版本变化，则升级数据库
            // 这里只处理 customer_info 表的升级逻辑，不删除 user_table 表
            if (oldVersion < 3) {
                // 升级时删除 customer_info 表并重建
                db.execSQL("DROP TABLE IF EXISTS customer_info");
                onCreate(db);  // 重新创建表
            }
        }
    }

    // 添加客户信息
    public long addCustomerInfo(String accountNumber, String password, String fullName, String idCard,
                                String phoneNumber, String email, String address) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("account_number", accountNumber);
        values.put("password", password);
        values.put("full_name", fullName);
        values.put("id_card", idCard);
        values.put("phone_number", phoneNumber);
        values.put("email", email);
        values.put("address", address);

        long result = db.insert("customer_info", null, values);
        Log.d("CustomerDbHelper", "Insert result: " + result);
        db.close(); // 关闭数据库
        return result;
    }

    @SuppressLint("Range")
    // 根据 customer_id 获取客户信息
    public CustomerInfo getCustomerInfo(int customerId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("customer_info", null, "customer_id = ?",
                new String[]{String.valueOf(customerId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int customerIdIndex = cursor.getColumnIndex("customer_id");
            int accountNumberIndex = cursor.getColumnIndex("account_number");
            int passwordIndex = cursor.getColumnIndex("password");
            int fullNameIndex = cursor.getColumnIndex("full_name");
            int idCardIndex = cursor.getColumnIndex("id_card");
            int phoneNumberIndex = cursor.getColumnIndex("phone_number");
            int emailIndex = cursor.getColumnIndex("email");
            int addressIndex = cursor.getColumnIndex("address");

            if (customerIdIndex != -1 && accountNumberIndex != -1 && passwordIndex != -1 && fullNameIndex != -1 &&
                    idCardIndex != -1 && phoneNumberIndex != -1 && emailIndex != -1 && addressIndex != -1) {
                CustomerInfo customerInfo = new CustomerInfo(
                        cursor.getInt(customerIdIndex),
                        cursor.getString(accountNumberIndex),
                        cursor.getString(passwordIndex),
                        cursor.getString(fullNameIndex),
                        cursor.getString(idCardIndex),
                        cursor.getString(phoneNumberIndex),
                        cursor.getString(emailIndex),
                        cursor.getString(addressIndex)
                );
                cursor.close();
                return customerInfo;
            }
        }
        cursor.close();
        return null;
    }

    // 查询所有客户信息
    @SuppressLint("Range")
    public List<CustomerInfo> getAllCustomers() {
        List<CustomerInfo> customers = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("customer_info", null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int customerId = cursor.getInt(cursor.getColumnIndex("customer_id"));
                String accountNumber = cursor.getString(cursor.getColumnIndex("account_number"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                String fullName = cursor.getString(cursor.getColumnIndex("full_name"));
                String idCard = cursor.getString(cursor.getColumnIndex("id_card"));
                String phoneNumber = cursor.getString(cursor.getColumnIndex("phone_number"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String address = cursor.getString(cursor.getColumnIndex("address"));

                CustomerInfo customerInfo = new CustomerInfo(customerId, accountNumber, password, fullName, idCard, phoneNumber, email, address);
                customers.add(customerInfo);
            }
            cursor.close();
        }
        db.close(); // 关闭数据库
        return customers;
    }

    // 更新客户信息
    public int updateCustomerInfo(int customerId, String accountNumber, String password, String email, String address, String phoneNumber) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("account_number", accountNumber);
        values.put("password", password);
        values.put("email", email);
        values.put("address", address);
        values.put("phone_number", phoneNumber);

        // 执行更新操作
        int rowsAffected = db.update("customer_info", values, "customer_id = ?", new String[]{String.valueOf(customerId)});
        db.close();
        return rowsAffected;
    }

    // 删除客户
    public void deleteCustomer(int customerId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("customer_info", "customer_id = ?", new String[]{String.valueOf(customerId)});
        db.close(); // 关闭数据库
    }
}
