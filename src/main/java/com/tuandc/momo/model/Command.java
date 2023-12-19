package com.tuandc.momo.model;

public enum Command {

    CASH_IN, // add amount for a user
    CREATE_USER, // create a user

    CREATE_BILL,
    LIST_BILL,
    SCHEDULE, // schedule payment date
    PAY, // user pays a bill
    LIST_PAYMENT,
    SEARCH_BILL_BY_PROVIDER,

    EXIT; // exit program
}
