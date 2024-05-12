package com.example.onlinemeditationapp;

public class SessionItem {
    private String name;
    private String desc;
    private String price;

    public SessionItem(String name, String desc, String price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String info) {
        this.desc = desc;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
