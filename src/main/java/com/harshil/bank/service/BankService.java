package com.harshil.bank.service;

import java.util.Scanner;
import java.util.ArrayList;
import java.math.BigDecimal;

import com.harshil.bank.dao.*;
import com.harshil.bank.model.*;

public class BankService{

    private UserDAO userDao = null;
    private AccountDAO accountDao = null;
    private TransactionDAO transactionDao = null; 

    // TODO update balance from double to BigDecimal

    public BankService(UserDAO userDao,AccountDAO accountDao,TransactionDAO transactionDao){
        this.userDao = userDao;
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
    }
    
    private String getAccountNumberFromUser(Scanner sc){
        String accountNumber = null;
        while(true){
            System.out.print("Enter Account Number: ");
            accountNumber = sc.nextLine();
            if(!userDao.existsBy("account_number", accountNumber)){
                System.out.println("Entered account number does not exists!!!");
                continue;
            }
            break;
        }
        return accountNumber;
    }

    private BigDecimal getAmountFromUser(Scanner sc,String txt){
        BigDecimal amount = null;
        while(true){
            System.out.print(txt);

            String amountString = sc.nextLine();
            try{
                amount = new BigDecimal(amountString);
            }catch(NumberFormatException  e){
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }

            // Check positive
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Amount must be positive.");
                continue;
            }

            // Check minimum 100
            if (amount.compareTo(new BigDecimal("100")) < 0) {
                System.out.println("Minimum amount is 100.");
                continue;
            }

            // Check decimal places
            if (amount.scale() > 2) {
                System.out.println("Only 2 decimal places allowed.");
                continue;
            }
            break;
        }
        return amount;
    }

    public void createUser(Scanner sc){
        System.out.print("Enter name: ");
        String name = sc.nextLine();

        String email = null;
        while(true){
            System.out.print("Enter email: ");
            email = sc.nextLine();
            if(!email.matches("^[A-Za-z0-9._%+-]+@gmail\\.com$")){
                System.out.println("Invalid Email Address!!!");
                continue;
            }
            if(!userDao.existsBy("email",email)) break;
            else System.out.println("Email already exists!!!");
        }

        String phoneNumber = null;
        while(true){
            System.out.print("Enter phone number: ");
            phoneNumber = sc.nextLine();
            if(phoneNumber.matches("^[6-9][0-9]{9}$")){
                System.out.println("Invalid Phone Number!!!");
                continue;
            }
            if(!userDao.existsBy("phone",phoneNumber)) break;
            else System.out.println("Phone number already exists!!!");
        }

        if(userDao.createUser(new User(name,email,phoneNumber))){
            System.out.println("User created successfully!!!");
        }

    }

    public void createAccount(Scanner sc){
        int id;
        while(true){
            System.out.print("Enter user ID: ");
            if(!sc.hasNextInt()){
                System.out.println("ID should be a natural number!!!");
                sc.nextLine();
                continue;
            }
            id = sc.nextInt();
            sc.nextLine();
            if(id < 1){
                System.out.println("ID should be a natural number!!!");
                continue;
            }
            if(!userDao.validateUserID(id)) System.out.println("Invalid user ID!!!");
            break;
        }
        
        String accountType = null;
        while(true){
            System.out.print("Enter Account Type (SAVINGS - 1 / CURRENT - 2): ");
            if(!sc.hasNextInt()){
                System.out.println("Enter a number 1 or 2!!!");
                sc.nextLine();
                continue;
            }
            int choice = sc.nextInt();
            sc.nextLine();
            switch(choice){
                case 1:
                    acocuntType = "SAVINGS";
                    break;
                case 2:
                    acocuntType = "CURRENT";
                    break;
                default:
                    System.out.println("Invalid choice!!!");
                    continue;
            }
            break;
        }

        Account account = new Account(id,account_type);
        if(accountDao.createAccount(account)){
            System.out.println("Account created successfully!!!");
            System.out.println("Account Number: " + account.getAccountNumber());
        }

    }

    public void deposit(Scanner sc){

        String accountNumber = getAccountNumberFromUser(sc);

        BigDecimal amount = getAmountFromUser(sc,"Enter Amount to Deposit: ");

        Transaction t = new Transaction(accountNumber,"deposite",amount);
        TransactionDAO.deposit(t);

        System.out.println("-----------------------------------------");
        System.out.println("Deposit Successful!");
        System.out.println("Deposited Amount: " + amount);
        System.out.println("Updated Balance: " +);
        System.out.println("Transaction ID: ");
        System.out.println("-----------------------------------------");

    }

    public void withdraw(Scanner sc){
        String accountNumber = getAccountNumberFromUser(sc);
        BigDecimal amount = getAmountFromUser(sc,"Enter Amount to Withdraw: ");

    }

    public void transfer(Scanner sc){

    }

    public void checkBalance(Scanner sc){

    }

    public void viewTransactionHistory(Scanner sc){

    }

}