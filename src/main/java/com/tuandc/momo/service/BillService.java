package com.tuandc.momo.service;

import com.tuandc.momo.model.Bill;
import com.tuandc.momo.model.BillStatus;
import com.tuandc.momo.model.BillType;
import com.tuandc.momo.model.Provider;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BillService {

    private final List<Bill> bills;

    public BillService() {
        bills = new ArrayList<>();
    }

    public void createBill(BillType type, int amount,
                           LocalDate dueDate,
                           Provider provider) {

        int id = this.bills.size() + 1;
        BillStatus status = BillStatus.NOT_PAID;
        Bill bill = new Bill(id, type, amount, dueDate, status, provider);
        this.bills.add(bill);
        displayBill(bill);
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void displayListOfBills() {
        System.out.println("Bill No.   Type   Amount     Due Date      State    PROVIDER");
        for (Bill bill : bills) {
            System.out.print(bill.getBillId() + ".        ");
            System.out.print(bill.getType() + "    ");
            System.out.print(bill.getAmount() + "    ");
            System.out.print(bill.getDueDate() + "    ");
            System.out.print(bill.getState() + "    ");
            System.out.println(bill.getProvider()+ "    ");
        }
    }

    private void displayBill(Bill bill) {
        bill.toString();
    }
}
