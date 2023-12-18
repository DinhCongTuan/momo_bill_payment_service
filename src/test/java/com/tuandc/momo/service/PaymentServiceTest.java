package com.tuandc.momo.service;

import com.tuandc.momo.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentServiceTest {
    private PaymentService paymentService;
    private Bill bill;
    private User user;

    @BeforeEach
    public void setUp() {
        paymentService = new PaymentService();
        bill = new Bill(1, BillType.ELECTRIC, 500,
                LocalDate.of(2023, 12, 31),
                BillStatus.NOT_PAID, Provider.EVN_HCMC);
        user = new User(1, "John", 1000);
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
        // Attempt to pay with insufficient funds
        user.setAvailableBalance(200);
        paymentService.pay(bill, user);
        List<PaymentTransaction> transactions = paymentService.getTransactions();

        // Check if no transaction was added
        assertEquals(1, transactions.size());

        // Check if the transaction state is 'SUCCEED'
        assertEquals(TransactionStatus.FAILED, transactions.get(0).getState());

        // Check if the user's balance has been updated correctly
        assertEquals(200, user.getAvailableBalance());
    }

}
