package com.mobiletrain.my.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/11/12 0012.
 */
public class UserData extends BmobObject {
    private String userName;
    private String password;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private String phoneNumber;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
