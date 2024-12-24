package com.example.myapplication.entity;

public class UserInfo {
    private  int user_id;
    private  String username;
    private  String password;
    private String tel;

    public static UserInfo sUserInfo;

    public static UserInfo getUserInfo(){
        return sUserInfo;
    }

    public static void setUserInfo(UserInfo userInfo){
        sUserInfo=userInfo;
    }

    public UserInfo(int user_id, String username, String password, String tel) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.tel = tel;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
