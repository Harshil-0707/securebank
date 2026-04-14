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

    public ServiceResponse<User> createUser(SignUpData sd){
      
        try(Connection connection = DBConnection.getConnection()){

            UserDAO userDao = new UserDAO(connection);

            if(userDao.existsBy("email",sd.getEmail())){
                return new ServiceResponse<>(false,"Email already registered",null);
            }

            if(userDao.existsBy("phone",sd.getPhoneNumber())){
                return new ServiceResponse<>(false,"Phone number already registered",null);
            }

            User user = userDao.createUser(sd);

            if(user != null){
                return new ServiceResponse<>(true,"Success",user);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return new ServiceResponse<>(false, "Something went wrong", null);
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
        ArrayList<Transaction> transactions = null;
        
        try(Connection connection = DBConnection.getConnection()){
            
            UserDAO userDao = new UserDAO(connection);
            User user = userDao.getUserData(userId);
            name = user.getName();

            AccountDAO accountDao = new AccountDAO(connection);
            Account account = accountDao.getAccountData(userId);
            balance = account.getBalance();

            TransactionDAO transactionDao = new TransactionDAO(connection);
            transactions = transactionDao.getTransactions(accountNumber,5);
            if(transactions.size() != 0){
                lastTransactionAmount = transactions.get(0).getTransactionAmount();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return new DashboardData(name,balance,lastTransactionAmount,accountNumber,transactions);
    }

    public Account getAccountData(int userId){
        try(Connection connection = DBConnection.getConnection()){
            return new AccountDAO(connection).getAccountData(userId);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Transaction> getAllTransactions(int userId,String accountNumber){

        ArrayList<Transaction> transactions = new ArrayList<>();

        try(Connection connection = DBConnection.getConnection()){

            TransactionDAO transactionDao = new TransactionDAO(connection);
            transactions = transactionDao.getTransactions(accountNumber,10);

        }catch(Exception e){
            e.printStackTrace();
        }
        return transactions;
    }

    public String deposit(TransactionData transactionData) {

        try (Connection con = DBConnection.getConnection()) {

            String accountNumber = transactionData.getAccountNumber();
            BigDecimal amount = transactionData.getAmount();

            BankService.logger.info(
                "Deposit initiated account={} amount={}",
                accountNumber,
                amount
            );

            AccountDAO accountDao = new AccountDAO(con);

            if (!accountDao.updateBalance(amount, accountNumber, "DEPOSIT")) {
                BankService.logger.warn(
                    "Deposit failed: Invalid account number={}",
                    accountNumber
                );
                return "Wrong account number";
            }

            Transaction transaction =
                new Transaction(accountNumber, "DEPOSIT", amount);

            TransactionDAO transactionDao = new TransactionDAO(con);

            if (!transactionDao.makeTransaction(transaction)) {
                BankService.logger.warn(
                    "Deposit failed: Transaction registration error account={}",
                    accountNumber
                );
                return "Transaction registration error";
            }

            BankService.logger.info(
                "Deposit successful account={} amount={}",
                accountNumber,
                amount
            );

            return "Success";

        } catch (Exception e) {
            e.printStackTrace();
            return "Something went wrong";
        }
    }

    public String withdraw(TransactionData transactionData) {

        try (Connection con = DBConnection.getConnection()) {

            String accountNumber = transactionData.getAccountNumber();
            BigDecimal amount = transactionData.getAmount();

            AccountDAO accountDao = new AccountDAO(con);

            BigDecimal availableBalance = accountDao.getBalance(accountNumber);
            BigDecimal minimumBalance = new BigDecimal("500");

            BankService.logger.info(
                "Withdrawal initiated account={} amount={}",
                accountNumber,
                amount
            );

            if (availableBalance.subtract(amount).compareTo(minimumBalance) < 0) {
                BankService.logger.warn(
                    "Withdraw failed: Minimum balance of 500 must be maintained."
                );
                throw new MinimumBalanceException(
                    "Minimum balance of 500 must be maintained."
                );
            }

            if (!accountDao.updateBalance(amount, accountNumber, "withdraw")) {
                return "Balance update failed";
            }

            TransactionDAO transactionDao = new TransactionDAO(con);
            Transaction transaction =
                new Transaction(accountNumber, "withdraw", amount);

            if (!transactionDao.makeTransaction(transaction)) {
                return "Transaction registration error";
            }

            BankService.logger.info("Withdrawal successful");
            return "Success";

        } catch (MinimumBalanceException e) {
            BankService.logger.warn(e.getMessage());
            return e.getMessage();

        } catch (Exception e) {
            e.printStackTrace();
            return "Something went wrong";
        }
    }

    public String transfer(TransferData transferData) {

        Connection con = null;

        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            String senderAccountNumber = transferData.getSenderAccountNumber();
            String receiverAccountNumber = transferData.getReceiverAccountNumber();
            BigDecimal amount = transferData.getAmount();

            if (senderAccountNumber.equals(receiverAccountNumber)) {
                logger.warn(
                    "Transfer failed: sender and receiver are same account={}",
                    senderAccountNumber
                );
                throw new SameAccountTransferException(
                    "Sender and receiver account numbers cannot be the same."
                );
            }

            AccountDAO accountDao = new AccountDAO(con);
            TransactionDAO transactionDao = new TransactionDAO(con);

            BigDecimal senderBalance = accountDao.getBalance(senderAccountNumber);

            if (amount.compareTo(senderBalance) > 0) {
                logger.warn(
                    "Transfer failed: insufficient balance account={} balance={} requested={}",
                    senderAccountNumber,
                    senderBalance,
                    amount
                );

                throw new InsufficientBalanceException(
                    "Available balance: " + senderBalance +
                    ", Transfer amount: " + amount
                );
            }

            boolean withdrawn =
                accountDao.updateBalance(amount, senderAccountNumber, "withdraw");

            if (!withdrawn) {
                throw new RuntimeException(
                    "Could not debit sender account."
                );
            }

            boolean deposited =
                accountDao.updateBalance(amount, receiverAccountNumber, "deposit");

            if (!deposited) {
                throw new RuntimeException(
                    "Could not credit receiver account."
                );
            }

            Transaction senderTransaction =
                new Transaction(senderAccountNumber, "TRANSFER_OUT", amount);

            Transaction receiverTransaction =
                new Transaction(receiverAccountNumber, "TRANSFER_IN", amount);

            if (!transactionDao.makeTransaction(senderTransaction)) {
                throw new RuntimeException(
                    "Sender transaction record failed."
                );
            }

            if (!transactionDao.makeTransaction(receiverTransaction)) {
                throw new RuntimeException(
                    "Receiver transaction record failed."
                );
            }

            con.commit();

            logger.info(
                "Transfer successful from={} to={} amount={}",
                senderAccountNumber,
                receiverAccountNumber,
                amount
            );

            return "Success";

        } catch (Exception e) {

            if (con != null) {
                try {
                    con.rollback();
                    logger.error("Transfer rolled back.");
                } catch (Exception rollbackEx) {
                    logger.error("Rollback failed", rollbackEx);
                }
            }

            logger.error("Transfer failed", e);
            return e.getMessage();

        } finally {

            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (Exception e) {
                    logger.error("Connection close error", e);
                }
            }
        }
    }

}