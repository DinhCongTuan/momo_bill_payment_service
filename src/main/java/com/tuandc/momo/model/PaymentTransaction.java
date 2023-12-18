package com.tuandc.momo.model;

import java.time.LocalDate;

public class PaymentTransaction {
    private int transactionId;
    private int billId;

    private int amount;
    private LocalDate paymentDate;
    private TransactionStatus state;

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public TransactionStatus getState() {
        return state;
    }

    public void setState(TransactionStatus state) {
        this.state = state;
    }
}
