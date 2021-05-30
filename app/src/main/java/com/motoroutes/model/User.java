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
}
