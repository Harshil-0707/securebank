package com.harshil.bank.dao;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.harshil.bank.model.Account;

public class AccountDAO{

    Connection con = null;

    public AccountDAO(Connection con){
        this.con = con;
    }

    public void createAccount(Account account){
        String sql = "INSERT INTO <table_name> () VALUES ()";

        try(PreparedStatement ps = con.prepareStatement(sql)){

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}