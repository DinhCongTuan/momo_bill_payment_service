package com.tuandc.momo;

import com.tuandc.momo.model.Bill;
import com.tuandc.momo.model.BillType;
import com.tuandc.momo.model.Command;
import com.tuandc.momo.model.Provider;
import com.tuandc.momo.service.BillService;
import com.tuandc.momo.service.PaymentService;
import com.tuandc.momo.service.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BillPaymentService {
    public static void main(String[] args) {
        System.out.println("Bill Payment Service Start ...");
        UserService userService = new UserService();
        BillService billService = new BillService();
        PaymentService paymentService = new PaymentService();

        initPaymentSchedule(userService, billService, paymentService);
        initCheckBillDueDateSchedule(billService);
        // Start a thread for handling user input
        Thread userInputThread = new Thread(() -> handleUserInput(userService, billService, paymentService));
        userInputThread.start();

    }

    private static void handleUserInput(UserService userService, BillService billService,
                                        PaymentService paymentService) {
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
            proceedCommand(command, tokens, userService, billService, paymentService);
        }
    }

    private static void proceedCommand(Command command, String[] tokens,
                                       UserService userService,
                                       BillService billService,
                                       PaymentService paymentService) {
        switch (command) {
            case CASH_IN: // CASH_IN Amount
                try {
                    int amount = Integer.parseInt(tokens[1]);
                    userService.addFunds(amount);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount for CASH_IN command.");
                }
                break;
            case VIEW_BALANCE:
                userService.displayUser();
                break;
            case CREATE_BILL: // CREATE_BILL BillType amount DueDate Provider
                                // CREATE_BILL WATER 1000 20/12/2023 SAVACO_HCMC
                try {
                    BillType billType = BillType.valueOf(tokens[1]);
                    int amount = Integer.parseInt(tokens[2]);
                    LocalDate dueDate = convertToLocalDate(tokens[3]);
                    Provider provider = Provider.valueOf(tokens[4]);
                    billService.createBill(billType, amount, dueDate, provider);
                } catch (Exception e) {
                    System.out.println("Invalid input");
                }
                break;

            case LIST_BILL:
                billService.displayListOfBills();
                break;
            case SCHEDULE: //SCHEDULE 2 20/12/2023
                try {
                    int billId = Integer.parseInt(tokens[1]);
                    LocalDate paymentDate = convertToLocalDate(tokens[2]);
                    paymentService.schedulePayment(billId, paymentDate);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input");
                }
                break;
            case PAY: //PAY billId1 billId2
                List<Bill> billsToPay = getBillsToPay(tokens, billService.getBills());
                paymentService.payMultipleBills(userService.getUser(), billsToPay);
                break;
            case LIST_PAYMENT:
                paymentService.displayListPayment();
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

    private static List<Bill> getBillsToPay(String[] tokens, List<Bill> allBills) {
        // Convert rest of the items to integers
        Set<Integer> billIDs = new HashSet<>(tokens.length - 1);
        for (int i = 1; i < tokens.length; i++) {
            try {
                billIDs.add(Integer.parseInt(tokens[i]));
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer found: " + tokens[i]);
                return List.of();
            }
        }

        return allBills.stream()
                .filter(bill -> billIDs.contains(bill.getBillId()))
                .toList();
    }

    private static LocalDate convertToLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return LocalDate.parse(dateString, formatter);
    }

    private static void initPaymentSchedule(UserService userService, BillService billService,
                                            PaymentService paymentService) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable task = () -> paymentService.processScheduledPayments(userService.getUser(), billService);
        scheduler.scheduleAtFixedRate(task, 0, 2, TimeUnit.MINUTES);
    }
    private static void initCheckBillDueDateSchedule(BillService billService) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable task = billService::checkDueDateOfBills;
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.MINUTES);
    }
}