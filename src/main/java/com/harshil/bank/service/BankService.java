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
            if(!accountDao.existsBy("account_number", accountNumber)){
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
            System.out.println("User created successfully!!!\n");
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
                    accountType = "SAVINGS";
                    break;
                case 2:
                    accountType = "CURRENT";
                    break;
                default:
                    System.out.println("Invalid choice!!!");
                    continue;
            }
            break;
        }

        Account account = new Account(id,accountType);
        if(accountDao.createAccount(account)){
            System.out.println("Account created successfully!!!");
            System.out.println("Account Number: " + account.getAccountNumber() + "\n");
        }

    }

    public void deposit(Scanner sc){

        String accountNumber = getAccountNumberFromUser(sc);

        BigDecimal amount = getAmountFromUser(sc,"Enter Amount to Deposit: ");
        
        if(!accountDao.updateBalance(amount,accountNumber,"deposite")){
            System.out.println("Error: Unable to updating balance!!!");
            return;
        }
        Transaction t = new Transaction(accountNumber,"deposite",amount);
        if(!transactionDao.makeTransaction(t)){
            System.out.println("Error: Unable to make transaction!!!");
            return;
        }

        System.out.println("-----------------------------------------");
        System.out.println("Deposit Successful!");
        System.out.println("Deposited Amount: " + amount);
        System.out.println("Updated Balance: " + accountDao.getBalance(accountNumber));
        System.out.println("Transaction ID: "+ t.getTransactionId());
        System.out.println("-----------------------------------------\n");

    }

    public void withdraw(Scanner sc){
        String accountNumber = getAccountNumberFromUser(sc);
        
        BigDecimal amount = getAmountFromUser(sc,"Enter Amount to Withdraw: ");
        BigDecimal availableBalance = accountDao.getBalance(accountNumber);
        if (amount.compareTo(availableBalance) <= 0) {
           
            if(!accountDao.updateBalance(amount,accountNumber,"withdraw")){
                System.out.println("could not withdraw!");
                return;
            }
            Transaction t = new Transaction(accountNumber,"withdraw",amount);

            transactionDao.makeTransaction(t);

            System.out.println("-----------------------------------------");
            System.out.println("Withdrawal Successful!");
            System.out.println("Withdrawn Amount: " + amount);
            System.out.println("Remaining Balance: " + availableBalance.subtract(amount));
            System.out.println("Transaction ID: "+ t.getTransactionId());
            System.out.println("-----------------------------------------\n");
        }else{
            System.out.println("Insufficient Balance!");
            System.out.println("-----------------------------------------");
            System.out.println("Error: Insufficient Balance!");
            System.out.println("Deposited Amount: " + amount);
            System.out.println("Available Balance: " + availableBalance);
            System.out.println("Transaction Cancelled.");
            System.out.println("-----------------------------------------\n");
            return;
        }

    }

    public void transfer(Scanner sc){

    }

    public void checkBalance(Scanner sc){
        String accountNumber = getAccountNumberFromUser(sc);

        Account a = accountDao.checkBalance(accountNumber);

        System.out.println("-----------------------------------------");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Type: " + a.getAccountType().toUpperCase());
        System.out.println("Current Balance: " + a.getBalance());
        System.out.println("-----------------------------------------\n");
    }

    public void viewTransactionHistory(Scanner sc) {
        String accountNumber = getAccountNumberFromUser(sc);

        ArrayList<Transaction> transactionHistory =
                transactionDao.getAllTransactions(accountNumber);

        String line = "------------------------------------------------------------------";

        System.out.println(line);

        // Header formatting
        System.out.printf("%-15s | %-10s | %-10s | %-15s%n",
                "Transaction ID", "Type", "Amount", "Date");

        System.out.println(line);

        // Row formatting
        for (Transaction t : transactionHistory) {
            System.out.printf("%-15d | %-10s | %-10.2f | %-15s%n",
                    t.getTransactionId(),
                    t.getTransactionType(),
                    t.getTransactionAmount(),
                    t.getTransactionTime()
            );
        }

        System.out.println(line + "\n");
    }

}