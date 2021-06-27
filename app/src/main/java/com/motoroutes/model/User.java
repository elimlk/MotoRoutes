package com.motoroutes.model;

public class User {

    public String userName;
    public String password;
    public String fullname;
    public String phone;

    public User() {
    }

    public User(String userName, String password, String fullname, String phone) {
        this.userName = userName;
        this.password = password;
        this.fullname = fullname;
        this.phone = phone;
    }

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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
