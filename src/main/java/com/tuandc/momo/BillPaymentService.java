package com.tuandc.momo;

import com.tuandc.momo.model.Command;
import com.tuandc.momo.service.PaymentService;
import com.tuandc.momo.service.UserService;

import java.util.Scanner;

public class BillPaymentService {
    public static void main(String[] args) {
        System.out.println("Bill Payment Service Start ...");

//        PaymentService paymentService = new PaymentService();
        UserService userService = new UserService();
        Scanner scanner = new Scanner(System.in);
        int operationCount = 1;
        boolean isProceed = true;

        while (isProceed) {
            operationCount ++;
            isProceed = operationCount < Integer.MAX_VALUE;
            System.out.print("$ ");
            String input = scanner.nextLine();
            String[] tokens = input.split(" ");

            if (tokens.length < 1) {
                System.out.println("Invalid command.");
                continue;
            }

            String commandStr = tokens[0];
            Command command;
            try {
                command = Command.valueOf(commandStr);
            } catch (IllegalArgumentException e) {
                System.out.println("String does not match any Enum constant: " + e.getMessage());
                continue;
            }

            switch (command) {
                case CREATE_USER: // CREATE_USER UserName
                    if (tokens.length < 2) {
                        System.out.println("Invalid parameters for CREATE_USER command.");
                        break;
                    }
                    userService.createUser(tokens[1]);
                    break;
                case CASH_IN: // CASH_IN UerId Amount
                    try {
                        int userId = Integer.parseInt(tokens[1]);
                        int amount = Integer.parseInt(tokens[2]);
                        userService.addFunds(userId, amount);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid userId/amount for CASH_IN command.");
                    }

                    break;
                case EXIT:
                    System.out.println("Exiting program.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Command not recognized.");
                    break;
            }
        }
    }
}