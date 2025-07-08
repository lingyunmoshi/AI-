package com.example.aibuysys.db.bean;

public class Account {
    public String user;
    public String pass;
    public String sex;
    public String phone;
    public String name;

    public Account(String user, String pass, String sex, String phone, String name) {
        this.user = user;
        this.pass = pass;
        this.sex = sex;
        this.phone = phone;
        this.name = name;
    }

    public Account() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
