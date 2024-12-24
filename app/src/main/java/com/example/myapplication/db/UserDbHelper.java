package com.example.myapplication.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.entity.UserInfo;

public class UserDbHelper extends SQLiteOpenHelper {
    private static UserDbHelper sHelper;
    private static final String DB_NAME = "user.db";   //数据库名
    private static final int VERSION = 3;    //版本号

    //必须实现其中一个构方法
    public UserDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //创建单例，供使用调用该类里面的的增删改查的方法
    public synchronized static UserDbHelper getInstance(Context context) {
        if (null == sHelper) {
            sHelper = new UserDbHelper(context, DB_NAME, null, VERSION);
        }
        return sHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建user_table表
        db.execSQL("create table user_table(user_id integer primary key autoincrement, " +
                "username text," +       //用户名
                "password text," +      //密码
                "tel TEXT" +                 // 电话
                ")");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if (oldVersion < newVersion) {
//            db.execSQL("DROP TABLE IF EXISTS user_table");
//            onCreate(db);
//        }
    }

    /*
    登录
     */

    @SuppressLint("Range")
    public UserInfo login(String username) {
        //获取SQLiteDatabase实例
        SQLiteDatabase db = getReadableDatabase();
        UserInfo userInfo = null;
        String sql = "select user_id,username,password,tel  from user_table where username=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor.moveToNext()) {
            int user_id = cursor.getInt(cursor.getColumnIndex("user_id"));
            String name = cursor.getString(cursor.getColumnIndex("username"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            String tel = cursor.getString(cursor.getColumnIndex("tel"));
            userInfo = new UserInfo(user_id, name, password, tel);
        }
        cursor.close();
        db.close();
        return userInfo;
    }

    @SuppressLint("Range")
    public int logout(String username) {
        // 获取SQLiteDatabase实例
        SQLiteDatabase db = getWritableDatabase(); // 通常删除操作需要可写数据库
        String whereClause = "username=?";
        String[] whereArgs = {username};
        int rowsDeleted = db.delete("user_table", whereClause, whereArgs);
        db.close();
        // 可以根据rowsDeleted的值来判断是否删除成功
        return rowsDeleted;
    }

    /*
    注册
     */
    public int register(String username, String password, String tel) {
        //获取SQLiteDatabase实例
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        //填充占位符
        values.put("username", username);
        values.put("password", password);
        values.put("tel", tel);
        String nullColumnHack = "values(null,?,?,?)";
        //执行
        int insert = (int) db.insert("user_table", nullColumnHack, values);
        db.close();
        return insert;
    }

    /*
    判断用户已存在
     */
    public boolean isUsernameExist(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT COUNT(*) FROM user_table WHERE username = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        if (cursor.moveToFirst() && cursor.getInt(0) > 0) {
            return true;
        }
        cursor.close();
        return false;
    }

    /**
     * 根据用户唯一 _id来修改密码
     */
    public int updatePwd(String username, String password) {
        //获取SQLiteDatabase实例
        SQLiteDatabase db = getWritableDatabase();
        // 填充占位符
        ContentValues values = new ContentValues();
        values.put("password", password);
        // 执行SQL
        int update = db.update("user_table", values, " username=?", new String[]{username+""});
        // 关闭数据库连接
        db.close();
        return update;

    }

    public int updateInfo(String username,String new_username, String tel) {
        //获取SQLiteDatabase实例
        SQLiteDatabase db = getWritableDatabase();
        // 填充占位符
        ContentValues values = new ContentValues();
        values.put("username",new_username);
        values.put("tel", tel);
        // 执行SQL
        int update = db.update("user_table", values, " username=?", new String[]{username+""});
        // 关闭数据库连接
        db.close();
        return update;

    }

}