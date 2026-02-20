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

}