package com.tuandc.momo;

import java.util.Scanner;

public class BillPaymentService {
    public static void main(String[] args) {
        System.out.println("Bill Payment Service Start ...");

        PaymentService paymentService = new PaymentService();
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
                case CREATE_USER:
                    if (tokens.length < 2) {
                        System.out.println("Invalid parameters for CREATE_USER command.");
                        break;
                    }
                    paymentService.createUser(tokens[1]);
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