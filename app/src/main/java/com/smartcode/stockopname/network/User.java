package com.smartcode.stockopname.network;

public class User {
    private int id;
    private String email, fullname, dealerID;

    public User(int id, String email, String fullname, String dealerID) {
        this.id = id;
        this.email = email;
        this.fullname = fullname;
        this.dealerID = dealerID;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getDealerID() {
        return dealerID;
    }
}
