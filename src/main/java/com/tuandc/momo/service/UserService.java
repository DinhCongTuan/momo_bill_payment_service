package com.tuandc.momo.service;

import com.tuandc.momo.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {

    private final List<User> users;

    public UserService() {
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

    public void addFunds(int userId, int amount) {
        Optional<User> optional = this.users.stream()
                .filter(user -> user.getUserId() == userId)
                .findFirst();
        if (optional.isEmpty()) {
            System.out.println("UserId is not exist");
            return;
        }

        User user  = optional.get();
        user.setAvailableBalance(user.getAvailableBalance() + amount);
        displayAll();
    }
    private void displayAll() {
        for (User user : users) {
            displayUser(user);
            System.out.println(" ----- ");
        }
    }

    private void displayUser(User user) {
        System.out.println("UserID: " + user.getUserId());
        System.out.println("Username: " + user.getUserName());
        System.out.println("BalanceAmount: " + user.getAvailableBalance());
    }
}
