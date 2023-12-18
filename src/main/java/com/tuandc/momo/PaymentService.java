package com.tuandc.momo;

import java.util.ArrayList;
import java.util.List;

public class PaymentService {

    private List<User> users;

    public PaymentService() {
        users = new ArrayList<>();
    }

    public void createUser(String userName) {
        for (User user : users) {
            if (userName.equalsIgnoreCase(user.getUserName())) {
                System.out.println("Username already exists");
                return;
            }
        }

        User user = new User();
        user.setUserId(users.size() + 1);
        user.setUserName(userName);
        users.add(user);
        displayUser(user);
    }

    private void displayUser(User user) {
        System.out.println("UserID: " + user.getUserId());
        System.out.println("Username: " + user.getUserName());
        System.out.println("BalanceAmount: " + user.getAvailableBalance());
    }
}
