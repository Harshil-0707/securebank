package com.harshil.bank.dao;

import java.math.BigDecimal;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.harshil.bank.model.Account;

public class AccountDAO{

    private static final Logger logger = LoggerFactory.getLogger(AccountDAO.class);

    private Connection con = null;

    public AccountDAO(Connection con){
        this.con = con;
    }

    public boolean createAccount(Account account){
        String sql = "INSERT INTO accounts (user_id,account_number,account_type,balance) VALUES (?,?,?,?)";

        int rowsAffected = 0;

        try(PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1,account.getUserId());
            ps.setString(2,account.getAccountNumber());
            ps.setString(3,account.getAccountType());
            ps.setBigDecimal(4,account.getBalance());
            rowsAffected = ps.executeUpdate();
        }catch(Exception e){
            AccountDAO.logger.error("Account creation Error: ",e);
        }
        return rowsAffected > 0;
    }

    public boolean updateBalance(BigDecimal amount,String accountNumber,String transactionType){
        
        String sql = null;
        if(transactionType.toLowerCase().equals("deposit")){
            sql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
        }else if(transactionType.toLowerCase().equals("withdraw")){
            sql = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
        }else{
            return false;
        }
        int rowsAffected = 0;
        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setBigDecimal(1,amount);
            ps.setString(2,accountNumber);
            rowsAffected = ps.executeUpdate();
        }catch(Exception e){
            AccountDAO.logger.error("Error updating balance: ",e);
        }
        return rowsAffected > 0;
    }

    public Account getAccountData(int id){
        String sql = "SELECT * FROM accounts WHERE user_id = ? ";

        Account account = null;

        try(PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1,id);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    account = new Account();
                    account.setUserId(id);
                    account.setAccountId(rs.getInt("account_id"));
                    account.setAccountNumber(rs.getString("account_number"));
                    account.setBalance(rs.getBigDecimal("balance"));
                    account.setAccountType(rs.getString("account_type"));
                    return account;
                }
            }catch(Exception e){
                AccountDAO.logger.error("Account creation Error: ",e);
            }

        }catch(Exception e){
            AccountDAO.logger.error("Error getting data for user id = {}",id,e);
        }

        return null;
    }
 
    public BigDecimal getBalance(String accountNumber){
        String sql = "SELECT balance FROM accounts WHERE account_number = ?";
        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1,accountNumber);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) return rs.getBigDecimal("balance");
            }catch(Exception e){
                AccountDAO.logger.error("Error setting balance for account number = {}",accountNumber,e);
            }
        }catch(Exception e){
            AccountDAO.logger.error("Error getting balance using account number = {}",accountNumber,e);
        }
        return new BigDecimal("0.0");
    }
}