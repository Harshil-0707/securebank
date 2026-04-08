package com.harshil.bank.service;

import java.util.ArrayList;
import java.math.BigDecimal;

import java.sql.Connection;

import com.harshil.bank.exception.*;

import com.harshil.bank.dto.*;

import com.harshil.bank.dao.*;
import com.harshil.bank.model.*;

import com.harshil.bank.util.DBConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankService{

    private static final Logger logger = LoggerFactory.getLogger(BankService.class);

    public BankService(){}

    public User createUser(SignUpData sd){
      
        try(Connection connection = DBConnection.getConnection()){

            UserDAO userDao = new UserDAO(connection);

            return userDao.createUser(
                new User(
                    sd.getName(),
                    sd.getEmail(),
                    sd.getPhoneNumber(),
                    sd.getPassword()
                )
            );

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public User UserExists(LoginData ld){
        try(Connection connection = DBConnection.getConnection()){
            return new UserDAO(connection).userExists(ld.getEmail(),ld.getPassword());
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Account createAccount(CreateAccountData cad){

        try(Connection con = DBConnection.getConnection()){
            UserDAO userDao = new UserDAO(con);
        
            if(!userDao.validateUserID(cad.getUserId())) return null;
            
            Account account = new Account(cad.getUserId(),cad.getAccountType(),cad.getBalance());

            AccountDAO accountDao = new AccountDAO(con);
            
            if(accountDao.createAccount(account)) return account;
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;

    }

    public DashboardData getDashboardData(int userId,String accountNumber){
        
        String name = null;
        BigDecimal balance = new BigDecimal("0.00");
        BigDecimal lastTransactionAmount = new BigDecimal("0.00");
        
        try(Connection connection = DBConnection.getConnection()){
            
            UserDAO userDao = new UserDAO(connection);
            User user = userDao.getUserData(userId);
            name = user.getName();

            AccountDAO accountDao = new AccountDAO(connection);
            Account account = accountDao.getAccountData(userId);
            balance = account.getBalance();

            TransactionDAO transactionDao = new TransactionDAO(connection);
            Transaction transaction = transactionDao.getLatestTransaction(accountNumber);
            if(transaction != null){
                lastTransactionAmount = transaction.getTransactionAmount();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return new DashboardData(name,balance,lastTransactionAmount);
    }

    public Account getAccountData(int userId){
        try(Connection connection = DBConnection.getConnection()){
            return new AccountDAO(connection).getAccountData(userId);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // public void deposit(){

    //     String accountNumber = getAccountNumberFromUser(sc,"Enter Account Number: ");
    //     if(accountNumber == null) return;

    //     BigDecimal amount = getAmountFromUser(sc,"Enter Amount to Deposit: ");

    //     Connection con = DBConnection.getConnection();

    //     AccountDAO accountDao = new AccountDAO(con);
        
    //     if(!accountDao.updateBalance(amount,accountNumber,"deposite")){
    //         System.out.println("Unable to updating balance!!!");
    //         return;
    //     }
    //     Transaction t = new Transaction(accountNumber,"deposite",amount);
    //     TransactionDAO transactionDao = new TransactionDAO(con);
    //     if(!transactionDao.makeTransaction(t)){
    //         System.out.println("Unable to make transaction!!!");
    //         return;
    //     }
        
    //     System.out.println("-----------------------------------------");
    //     System.out.println("Deposit Successful!");
    //     System.out.println("Deposited Amount: " + amount);
    //     System.out.println("Updated Balance: " + accountDao.getBalance(accountNumber));
    //     System.out.println("Transaction ID: "+ t.getTransactionId());
    //     System.out.println("-----------------------------------------\n");

    //     BankService.logger.info("Deposit successful account number = {} amount = {}",accountNumber,amount);

    // }

    // public void withdraw() throws Exception{
        
    //     String accountNumber = getAccountNumberFromUser(sc,"Enter Account Number: ");
    //     if(accountNumber == null) return;
        
    //     BigDecimal amount = getAmountFromUser(sc,"Enter Amount to Withdraw: ");

    //     Connection con = DBConnection.getConnection();

    //     AccountDAO accountDao = new AccountDAO(con);

    //     BigDecimal availableBalance = accountDao.getBalance(accountNumber);
    //     BigDecimal minimumBalance = new BigDecimal("500");

    //     if (availableBalance.subtract(amount).compareTo(minimumBalance) < 0) {
           
    //         BankService.logger.info("Withdrawal initiated account={} amount={}", accountNumber,amount);

    //         if(!accountDao.updateBalance(amount,accountNumber,"withdraw")){
    //             BankService.logger.warn("Withdraw failed: Minimum balance of 500 must be maintained.");
    //             throw new MinimumBalanceException("Minimum balance of 500 must be maintained.");
    //         }

    //         TransactionDAO transactionDao = new TransactionDAO(con);

    //         Transaction t = new Transaction(accountNumber,"withdraw",amount);

    //         transactionDao.makeTransaction(t);

    //         System.out.println("-----------------------------------------");
    //         System.out.println("Withdrawal Successful!");
    //         System.out.println("Withdrawn Amount: " + amount);
    //         System.out.println("Remaining Balance: " + availableBalance.subtract(amount));
    //         System.out.println("Transaction ID: "+ t.getTransactionId());
    //         System.out.println("-----------------------------------------\n");

    //         BankService.logger.info("Withdrawal successful");

    //     }else{
    //         BankService.logger.warn("Withdarw failed: Available balance is less than withdraw amound.");
    //         throw new InsufficientBalanceException(
    //             "Available balance: " + availableBalance + ", Tried to withdraw: " + amount
    //         );
    //     }

    // }

    // public void transfer() throws Exception {
       
    //     String senderAccountNumber = getAccountNumberFromUser(sc,"Enter Sender Account Number: ");
    //     if(senderAccountNumber == null) return;
       
    //     String receiverAccountNumber = getAccountNumberFromUser(sc,"Enter Receiver Account Number: ");
    //     if(receiverAccountNumber == null) return;
        
    //     Connection con = DBConnection.getConnection();

    //     if(senderAccountNumber.equals(receiverAccountNumber)){
    //         logger.warn("Transfer failed: sender and receiver account are the same account={}",
    //         senderAccountNumber);
    //         throw new SameAccountTransferException("Sender and Receiver's account numbers cannot be same.");
    //     }
            
    //     BigDecimal amount = getAmountFromUser(sc,"Enter Amount to Transfer: ");

    //     AccountDAO accountDao = new AccountDAO(con);

    //     BigDecimal senderAvailableBalance = accountDao.getBalance(senderAccountNumber);
    //     if (amount.compareTo(senderAvailableBalance) > 0) {
    //         logger.warn("Transfer failed: Available balance is less than withdraw balance");
    //         throw new InsufficientBalanceException(
    //             "Available balance: " + senderAvailableBalance + ", Tried to withdraw: " + amount
    //         );
    //     }

    //     try{
    //         con.setAutoCommit(false);

    //         System.out.println("\n-----------------------------------------");
    //         System.out.println("Processing Transfer...");
    //         System.out.println("-----------------------------------------");
            
    //         if(!accountDao.updateBalance(amount,senderAccountNumber,"withdraw")){
    //             throw new RuntimeException("Could not Transfer money from sender's account!!!");
    //         }
           
    //         if(!accountDao.updateBalance(amount,receiverAccountNumber,"deposite")){
    //             throw new RuntimeException("Could not Transfer money to receiver's account!!!");
    //         }
            
    //         BankService.logger.info("Transfer initiated from={} to={} amount={}",senderAccountNumber,receiverAccountNumber,amount);
           
    //         Transaction t1 = new Transaction(senderAccountNumber,"TRANSFER_OUT",amount);
            
    //         TransactionDAO transactionDao = new TransactionDAO(con);

    //         if(!transactionDao.makeTransaction(t1)){
    //             throw new RuntimeException("Transaction record failed");
    //         }

    //         Transaction t2 = new Transaction(receiverAccountNumber,"TRANSFER_IN",amount);

    //         if(!transactionDao.makeTransaction(t2)){
    //             throw new RuntimeException("Transaction record failed");
    //         }

    //         con.commit();

    //         BankService.logger.info("Transfer from={} to={} amount={} successful.",senderAccountNumber,receiverAccountNumber,amount);

    //         System.out.println("Transfer Successful!");
    //         System.out.println("Transferred Amount: " + amount);
    //         System.out.println("Sender New Balance: " + (senderAvailableBalance.subtract(amount)));
    //         System.out.println("Receiver New Balance: " + (accountDao.getBalance(receiverAccountNumber)));
    //         System.out.println("-----------------------------------------");
       
    //     }catch(Exception e){
    //         try {
    //             con.rollback();
    //             System.out.println("Transfer Failed! Transaction Rolled Back.");
    //             BankService.logger.error("Transfer failed: ",e);
    //         } catch (Exception ex) {
    //             BankService.logger.error("Transfer rollback error: ",ex);
    //             ex.printStackTrace();
    //         }
    //     }finally{
    //         try {
    //             con.setAutoCommit(true);
    //         } catch (Exception e) {
    //             BankService.logger.error("AutoCommit error: ",e);
    //             e.printStackTrace();
    //         }
    //     }
    // }

    // public void checkBalance(){
    //     String accountNumber = getAccountNumberFromUser(sc,"Enter Account Number: ");
    //     if(accountNumber == null) return;

    //     Connection con = DBConnection.getConnection();

    //     AccountDAO accountDao = new AccountDAO(con);

    //     Account a = accountDao.checkBalance(accountNumber);

    //     System.out.println("-----------------------------------------");
    //     System.out.println("Account Number: " + accountNumber);
    //     System.out.println("Account Type: " + a.getAccountType().toUpperCase());
    //     System.out.println("Current Balance: " + a.getBalance());
    //     System.out.println("-----------------------------------------\n");
    //     BankService.logger.info("Checked Balance");
    // }

    // public void viewTransactionHistory() {
    //     String accountNumber = getAccountNumberFromUser(sc,"Enter Account Number: ");
    //     if(accountNumber == null) return;

    //     Connection con = DBConnection.getConnection();

    //     TransactionDAO transactionDao = new TransactionDAO(con);

    //     ArrayList<Transaction> transactionHistory =
    //             transactionDao.getAllTransactions(accountNumber);

    //     String line = "------------------------------------------------------------------";

    //     System.out.println(line);

    //     // Header formatting
    //     System.out.printf("%-15s | %-10s | %-10s | %-15s%n",
    //             "Transaction ID", "Type", "Amount", "Date");

    //     System.out.println(line);

    //     // Row formatting
    //     for (Transaction t : transactionHistory) {
    //         System.out.printf("%-15d | %-10s | %-10.2f | %-15s%n",
    //                 t.getTransactionId(),
    //                 t.getTransactionType(),
    //                 t.getTransactionAmount(),
    //                 t.getTransactionTime()
    //         );
    //     }

    //     System.out.println(line + "\n");
    //     BankService.logger.info("Viewed Transaction History");
    // }

}