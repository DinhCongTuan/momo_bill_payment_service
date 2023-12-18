package com.tuandc.momo.model;

import java.time.LocalDate;

public class Bill {

    private int billId;
    private BillType type;
    private int amount;
    private LocalDate dueDate;
    private BillStatus state;
    private Provider provider;

    public Bill(int id, BillType type, int amount,
                LocalDate dueDate, BillStatus state,
                Provider provider) {
        this.billId = id;
        this.type = type;
        this.amount = amount;
        this.dueDate = dueDate;
        this.state = state;
        this.provider = provider;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "billId=" + billId +
                ", type=" + type +
                ", amount=" + amount +
                ", dueDate=" + dueDate +
                ", state=" + state +
                ", provider=" + provider +
                '}';
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public BillType getType() {
        return type;
    }

    public void setType(BillType type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BillStatus getState() {
        return state;
    }

    public void setState(BillStatus state) {
        this.state = state;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
