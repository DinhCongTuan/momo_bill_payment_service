package com.tuandc.momo.service;

import com.tuandc.momo.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {

    private final User user;

    public UserService() {
        user = new User();
        user.setUserId(1);
        user.setUserName("default user name");
    }

    public User getUser() {
        return user;
    }

    public void addFunds(int amount) {
        this.user.setAvailableBalance(user.getAvailableBalance() + amount);
        displayUser();
    }

    private void displayUser() {
        System.out.println("UserID: " + user.getUserId());
        System.out.println("Username: " + user.getUserName());
        System.out.println("BalanceAmount: " + user.getAvailableBalance());
    }
}
