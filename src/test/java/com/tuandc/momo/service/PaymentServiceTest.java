package com.tuandc.momo.service;

import com.tuandc.momo.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentServiceTest {
    private PaymentService paymentService;
    private Bill bill;
    private User user;

    private Bill bill1;
    private Bill bill2;

    @BeforeEach
    public void setUp() {
        paymentService = new PaymentService();
        bill = new Bill(1, BillType.ELECTRIC, 500,
                LocalDate.of(2023, 12, 31),
                BillStatus.NOT_PAID, Provider.EVN_HCMC);
        user = new User(1, "John", 1000);

        bill1 = new Bill(1, BillType.ELECTRIC, 200, LocalDate.now(),
                BillStatus.NOT_PAID, Provider.EVN_HCMC);
        bill2 = new Bill(2, BillType.WATER, 300, LocalDate.now(),
                BillStatus.NOT_PAID, Provider.SAVACO_HCMC);

    }
    @Test
    void testPayMultipleBills_Success() {
        List<Bill> billsToPay = new ArrayList<>();
        billsToPay.add(bill1);
        billsToPay.add(bill2);

        int initialBalance = user.getAvailableBalance();
        paymentService.payMultipleBills(user, billsToPay);

        assertEquals(2, paymentService.getTransactions().size());

        int expectedBalance = initialBalance - (bill1.getAmount() + bill2.getAmount());
        assertEquals(expectedBalance, user.getAvailableBalance());
        assertEquals(BillStatus.PAID, bill1.getState());
        assertEquals(BillStatus.PAID, bill2.getState());
    }

    @Test
    void testPayMultipleBills_InsufficientBalance() {
        // Create a bill with an amount higher than the user's balance
        Bill bill3 = new Bill(3, BillType.INTERNET, 2000, LocalDate.now(),
                BillStatus.NOT_PAID, Provider.VNPT);
        List<Bill> billsToPay = new ArrayList<>();
        billsToPay.add(bill3);

        int initialBalance = user.getAvailableBalance();
        paymentService.payMultipleBills(user, billsToPay);

        assertEquals(0, paymentService.getTransactions().size());
        assertEquals(initialBalance, user.getAvailableBalance());
        assertEquals(BillStatus.NOT_PAID, bill3.getState());
    }

    @Test
    void testPay_SuccessfulPayment() {
        paymentService.pay(bill, user);
        List<PaymentTransaction> transactions = paymentService.getTransactions();

        assertEquals(1, transactions.size());

        assertEquals(TransactionStatus.SUCCEED, transactions.get(0).getState());

        assertEquals(500, user.getAvailableBalance());
        assertEquals(BillStatus.PAID, bill.getState());
    }

    @Test
    void testPay_InsufficientFunds() {
        user.setAvailableBalance(200);
        paymentService.pay(bill, user);
        List<PaymentTransaction> transactions = paymentService.getTransactions();

        assertEquals(1, transactions.size());
        assertEquals(TransactionStatus.FAILED, transactions.get(0).getState());
        assertEquals(200, user.getAvailableBalance());
    }

}
