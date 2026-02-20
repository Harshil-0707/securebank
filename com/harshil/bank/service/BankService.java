package com.harshil.bank.service;

import java.util.Scanner;
import java.util.ArrayList;
import com.harshil.bank.dao.*;
import com.harshil.bank.model.*;

public class BankService{

    private UserDAO userDao = null;
    private AccountDAO accountDao = null;
    private TransactionDAO transactionDao = null; 

    public BankService(UserDAO userDao,AccountDAO accountDao,TransactionDAO transactionDao){
        this.userDao = userDao;
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
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
            if(null == userDao.searchByEmail(email)) break;
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
            if(null == userDao.searchByPhone(phoneNumber)) break;
            else System.out.println("Phone number already exists!!!");
        }

    }

    public void createAccount(Scanner sc){
        System.out.print("Enter user ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Account Type (SAVINGS/CURRENT): ");
        String accountType = sc.nextLine();
    }

    public void deposit(Scanner sc){

        String accountNumber = null;
        while(true){
            System.out.print("Enter Account Number: ");
            accountNumber = sc.nextLine();
            if(!userDao.existsBy("account_number", accountNumber)){
                System.out.println("Entered account number does not exists!!!");
                continue;
            }
        }

        double amount = 0;

        while(true){
            System.out.print("Enter Amount to Deposit: ");

            if(!sc.hasNextDouble() || !sc.hasNextInt()){
                System.out.println("Amount should be a number!!!");
                sc.nextLine();
                continue;
            }
            amount = sc.nextDouble();
            sc.nextLine();
            if(amount < 100){
                System.out.println("Amount should be more than 100!!!");
                continue;
            }
            break;
        }

        System.out.println("-----------------------------------------");
        System.out.println("Deposit Successful!");
        System.out.println("Deposited Amount: ");
        System.out.println("Updated Balance: ");
        System.out.println("Transaction ID: ");
        System.out.println("-----------------------------------------");

    }

    public void withdraw(Scanner sc){
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

        double amount = 0;

        while(true){
            System.out.print("Enter Amount to Withdraw: ");

            if(!sc.hasNextDouble() || !sc.hasNextInt()){
                System.out.println("Amount should be a number!!!");
                sc.nextLine();
                continue;
            }
            amount = sc.nextDouble();
            sc.nextLine();
            if(amount < 100){
                System.out.println("Amount should be more than 100!!!");
                continue;
            }
            break;
        }
    }

    public void transfer(Scanner sc){

    }

    public void checkBalance(Scanner sc){

    }

    public void viewTransactionHistory(Scanner sc){

    }

}