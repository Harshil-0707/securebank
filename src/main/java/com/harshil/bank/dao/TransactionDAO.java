package com.harshil.bank.dao;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.harshil.bank.model.Transaction;

public class TransactionDAO{
    
    Connection con = null;

    public TransactionDAO(Connection con){
        this.con = con;
    }

    public boolean deposit(Transaction t){
        String sql = "INSERT INTO transactions (account_number,type,amount) VALUES (?,?,?)";
        int rowsAffected = 0;
        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1,t.getAccountNumber());
            ps.setString(2,t.getAccountType());
            ps.setBigDecimal(3,t.getTransactionAmount());
            rowsAffected = ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        return rowsAffected > 0;
    }

}