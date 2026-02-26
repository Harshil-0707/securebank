package com.harshil.bank.dao;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.time.LocalDateTime;

import com.harshil.bank.model.Transaction;

public class TransactionDAO{
    
    Connection con = null;

    public TransactionDAO(Connection con){
        this.con = con;
    }

    public boolean makeTransaction(Transaction t){
        String sql = "INSERT INTO transactions (account_number,type,amount) VALUES (?,?,?)";
        int rowsAffected = 0;
        try(PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,t.getAccountNumber());
            ps.setString(2,t.getAccountType());
            ps.setBigDecimal(3,t.getTransactionAmount());
            rowsAffected = ps.executeUpdate();
            t.setTransactionId();
        }catch(Exception e){
            e.printStackTrace();
        }
        return rowsAffected > 0;
    }

    public ArrayList<Transaction> getAllTransactions(String accountNumber){
        String sql = "SELECT * FROM transactions WHERE account_number = ? ORDER BY transaction_date";

        ArrayList<Transaction> transactions = null;

        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1,accountNumber);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    int id = rs.getInt("transaction_id");
                    String type = rs.getString("type");
                    BigDecimal amount = rs.getBigDecimal("amount");
                    LocalDateTime date = rs.getTimeStamp("transaction_date").toLocalDateTime();
                    Transaction t = new Transaction(accountNumber,type,amount);
                    t.setTransactionTime(date);
                    t.setTransactionId(id);
                    transactions.add(t);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return transactions;
    }
}