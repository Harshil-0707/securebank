package com.harshil.bank.dao;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.harshil.bank.model.Account;

public class AccountDAO{

    Connection con = null;

    // TODO update balance from double to BigDecimal

    public AccountDAO(Connection con){
        this.con = con;
    }

    public boolean createAccount(Account account){
        String sql = "INSERT INTO accounts (user_id,account_number,account_type) VALUES (?,?,?)";

        int rowsAffected = 0;

        try(PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1,account.getUserId());
            ps.setString(2,account.getAccountNumber());
            ps.setString(3,account.getAccountType());
            rowsAffected = ps.executeUpdate();
            System.out.println(account.getAccountNumber());
        }catch(Exception e){
            e.printStackTrace();
        }
        return rowsAffected > 0;
    }

    public boolean existsBy(String fieldName,String value){
        String sql = "SELECT 1 FROM accounts WHERE " + fieldName + " = ?";
        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1,value);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateBalance(double amount,String accountNumber){
        String sql = "UPDATE balance SET balance = balance + ? WHERE account_number = ?";
        int rowsAffected = 0;
        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setDouble(1,amount);
            ps.setString(2,accountNumber);
            rowsAffected = ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
        return rowsAffected > 0;
    }
    
    private double getUpdatedAmount(String accountNumber){
        String sql = "SELECT balance FROM accounts WHERE account_number = ?";
        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1,accountNumber);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return rs.getBigDecimal("balance");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0.0;
    }

}