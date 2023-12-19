package com.tuandc.momo.service;

import com.tuandc.momo.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {

    private final List<PaymentTransaction> transactions;

    public PaymentService() {
        transactions = new ArrayList<>();
    }

    public List<PaymentTransaction> getTransactions() {
        return transactions;
    }

    public void payMultipleBills(User user, List<Bill> billsToPay) {
        int totalAmount = 0;
        for (Bill bill : billsToPay) {
            totalAmount += bill.getAmount();
        }

        int availableBalance = user.getAvailableBalance();
        if (availableBalance < totalAmount) {
            System.out.println("Sorry! Not enough fund to proceed with payment.");
            return;
        }

        for (Bill bill : billsToPay) {
            //validate status and due date of the Bill -> by pass
            PaymentTransaction transaction = new PaymentTransaction();
            transaction.setTransactionId(this.transactions.size() + 1);
            transaction.setAmount(bill.getAmount());
            transaction.setBillId(bill.getBillId());
            transaction.setPaymentDate(LocalDate.now());

            transaction.setState(TransactionStatus.SUCCEED);
            this.transactions.add(transaction);

            availableBalance -= bill.getAmount();
            bill.setState(BillStatus.PAID);
            System.out.println("Payment has been completed for Bill with id: " + bill.getBillId());
        }
        user.setAvailableBalance(availableBalance);
        System.out.println("Your current balance is:" + user.getAvailableBalance());
    }

    public void pay(Bill bill, User user) {
        if (bill == null) return;
        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setTransactionId(this.transactions.size() + 1);
        transaction.setAmount(bill.getAmount());
        transaction.setBillId(bill.getBillId());
        transaction.setPaymentDate(LocalDate.now());

        int availableBalance = user.getAvailableBalance();
        // validate available balance of user
        if (availableBalance < bill.getAmount()) {
            transaction.setState(TransactionStatus.FAILED);
            System.out.println("Sorry! Not enough fund to proceed with payment.");
        } else {
            transaction.setState(TransactionStatus.SUCCEED);
            int fund = availableBalance - bill.getAmount();
            user.setAvailableBalance(fund);
            bill.setState(BillStatus.PAID);
            System.out.println("Payment has been completed for Bill with id: " + bill.getBillId());
            System.out.println("Your current balance is:" + user.getAvailableBalance());
        }

        transactions.add(transaction);
    }

    public void displayListPayment() {
        System.out.println("No.   Amount    Payment Date     State    Bill Id");
        for (PaymentTransaction transaction : transactions) {
            System.out.print(transaction.getTransactionId() + ".     ");
            System.out.print(transaction.getAmount() + "    ");
            System.out.print(transaction.getPaymentDate() + "    ");
            System.out.print(transaction.getState() + "    ");
            System.out.println(transaction.getBillId() + "    ");
        }
    }

}
