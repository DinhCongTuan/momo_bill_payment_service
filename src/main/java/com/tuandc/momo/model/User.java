package com.tuandc.momo.model;

import java.util.List;

public class User {

    private int userId;

    private String userName;

    private int availableBalance;

    public User() {
    }

    public User(int userId, String userName, int availableBalance) {
        this.userId = userId;
        this.userName = userName;
        this.availableBalance = availableBalance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(int availableBalance) {
        this.availableBalance = availableBalance;
    }
}
