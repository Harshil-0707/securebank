package com.harshil.bank.dto;

import java.util.ArrayList;
import java.math.BigDecimal;

import com.harshil.bank.model.Transaction;

public class DashboardData{

    private String name;
    private BigDecimal balance;
    private BigDecimal lastTransactionAmount;
    private String accountNumber;
    private ArrayList<Transaction> transactions;

    public DashboardData(){}

    public DashboardData(String name,BigDecimal balance,BigDecimal lastTransactionAmount,String accountNumber,ArrayList<Transaction> transactions){
        this.name = name;
        this.balance = balance;
        this.lastTransactionAmount = lastTransactionAmount;
        this.accountNumber = accountNumber;
        this.transactions = transactions;
    }

    // getters

    public String getName(){
        return this.name;
    }

    public BigDecimal getBalance(){
        return this.balance;
    }

    public BigDecimal getLastTransactionAmount(){
        return this.lastTransactionAmount;
    }

    public String getAccountNumber(){
        return this.accountNumber;
    }

    public ArrayList<Transaction> getTransactions(){
        return this.transactions;
    }

}