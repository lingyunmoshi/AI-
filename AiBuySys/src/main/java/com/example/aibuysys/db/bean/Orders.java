package com.example.aibuysys.db.bean;

public class Orders {
    public String user;
    public int exhibition_id;
    public int quantity;
    public int money;
    public String paymethod;
    public String time;

public Orders(){}
    public Orders(String user, int exhibition_id, int quantity, int money, String paymethod, String time) {
        this.user = user;
        this.exhibition_id = exhibition_id;
        this.quantity = quantity;
        this.money = money;
        this.paymethod = paymethod;
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getExhibition_id() {
        return exhibition_id;
    }

    public void setExhibition_id(int exhibition_id) {
        this.exhibition_id = exhibition_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(String paymethod) {
        this.paymethod = paymethod;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
