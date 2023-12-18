package com.tuandc.momo.service;

import com.tuandc.momo.model.Bill;
import com.tuandc.momo.model.BillStatus;
import com.tuandc.momo.model.BillType;
import com.tuandc.momo.model.Provider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BillServiceTest {

    private BillService billService;

    @BeforeEach
    public void setUp() {
        billService = new BillService();
    }

    @Test
    void testCreateBill() {
        // Create a bill
        billService.createBill(BillType.ELECTRIC, 500,
                LocalDate.of(2023, 12, 31),
                Provider.EVN_HCMC);

        List<Bill> bills = billService.getBills();
        assertEquals(1, bills.size());

        Bill createdBill = bills.get(0);
        assertEquals(BillType.ELECTRIC, createdBill.getType());
        assertEquals(500, createdBill.getAmount());
        assertEquals(LocalDate.of(2023, 12, 31), createdBill.getDueDate());
        assertEquals(BillStatus.NOT_PAID, createdBill.getState());
        assertEquals(Provider.EVN_HCMC, createdBill.getProvider());
    }
}
