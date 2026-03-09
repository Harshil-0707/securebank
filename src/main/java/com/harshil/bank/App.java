package com.harshil.bank;

import java.util.Scanner;
import java.sql.Connection;

import com.harshil.bank.dao.*;
import com.harshil.bank.util.DBConnection;
import com.harshil.bank.service.BankService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.harshil.bank.exception.*;

public class App{
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    
    public static void main(String[] args){

        UserDAO userDao = null;
        AccountDAO accountDao = null;
        TransactionDAO transactionDao = null;
        Scanner sc = new Scanner(System.in);

        App.logger.info("Banking system started");

        try(Connection connection = DBConnection.getConnection()){
            userDao = new UserDAO(connection);
            accountDao = new AccountDAO(connection);
            transactionDao = new TransactionDAO(connection);

            BankService bs = new BankService(userDao,accountDao,transactionDao);

            boolean running = true;

            while(running){
                System.out.println("---------------------------------");
                System.out.println("     WELCOME TO SECUREBANK");
                System.out.println("---------------------------------");
                System.out.println("1. Create User");
                System.out.println("2. Create Account");
                System.out.println("3. Deposit");
                System.out.println("4. Withdraw");
                System.out.println("5. Transfer Money");
                System.out.println("6. Check Balance");
                System.out.println("7. View Transactions");
                System.out.println("8. Exit");
                System.out.print("Enter choice: ");
                if(!sc.hasNextInt()){
                    System.out.println("You should enter a number between 1 to 8!!!");
                    sc.nextLine();
                    continue;
                }
                int choice = sc.nextInt();
                sc.nextLine();
                switch(choice){
                    case 1:
                        bs.createUser(sc);
                        break;
                    case 2:
                        bs.createAccount(sc);
                        break;
                    case 3:
                        bs.deposit(sc);
                        break;
                    case 4:
                        try{
                            bs.withdraw(sc);
                        }catch(Exception e){
                            System.out.println("-----------------------------------------");
                            System.out.println("Error: " + e.getMessage());
                            System.out.println("Transaction Cancelled.");
                            System.out.println("-----------------------------------------");
                        }
                        break;
                    case 5:
                        try{
                            bs.transfer(sc);
                        }catch(Exception e){
                            System.out.println("-----------------------------------------");
                            System.out.println("Error: " + e.getMessage());
                            System.out.println("Transaction Cancelled.");
                            System.out.println("-----------------------------------------");
                        }
                        break;
                    case 6:
                        bs.checkBalance(sc);
                        break;
                    case 7:
                        bs.viewTransactionHistory(sc);
                        break;
                    case 8:
                        running = false;
                        System.out.println("-----------------------------------------");
                        System.out.println("  Thank you for using SecureBank!");
                        System.out.println("  Goodbye");
                        System.out.println("-----------------------------------------");
                        break;
                    default:
                        System.out.println("Invalid choice!!!");
                }
            }
        }catch(Exception e){
            App.logger.error("Error:: ",e);
            e.printStackTrace();
        }finally{
            try {
                com.mysql.cj.jdbc.AbandonedConnectionCleanupThread.checkedShutdown();
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    App.logger.info("Application shutting down");
                    App.logger.info("Database connection closed");
                }));
            } catch (Exception e) {
                App.logger.error("Error closing application ",e);
                e.printStackTrace();
            }
        
        }

        sc.close();
    }
}