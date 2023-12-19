# momo_bill_payment_service
Interview test of MOMO company: bill payent service

## How to run
> javac src/main/java/com/tuandc/momo/**/*.java

> java -classpath src/main/java com.tuandc.momo.BillPaymentService

## Commands set
> CASH_IN 1000

> VIEW_BALANCE

> CREATE_BILL WATER 1000 20/12/2023 SAVACO_HCMC

> LIST_BILL

> SCHEDULE 2 19/12/2023

> PAY 1 2

> LIST_PAYMENT

> EXIT

## Classes
1. User
2. Bill
3. PaymentTransaction
4. Services
5. Main

## Implementation step
1. Start program with command input
2. Create User, Bill, PaymentTransaction model classes
3. Create Service classes
- create user
- add funds
4. Create Bill service
- createBill
- updateBill
- list bills
- searchBillByProvider
5. PaymentTransaction
- Pay
- list payments
6. schedule
- pay bills
- check due date of bills