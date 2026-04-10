package com.harshil.bank.dto;

import java.util.ArrayList;
import java.math.BigDecimal;

import com.harshil.bank.model.Transaction;

public class DashboardData{

    private String name;
    private BigDecimal balance;
    private BigDecimal lastTransactionAmount;
    private ArrayList<Transaction> transactions;

    public DashboardData(){}

    public DashboardData(String name,BigDecimal balance,BigDecimal lastTransactionAmount,ArrayList<Transaction> transactions){
        this.name = name;
        this.balance = balance;
        this.lastTransactionAmount = lastTransactionAmount;
        this.transactions = transactions;
    }

    // setters

    public void setName(String name){
        this.name = name;
    }

    public void setBalance(BigDecimal balance){
        this.balance = balance;
    }

    public void setLastTransactionAmount(BigDecimal lastTransactionAmount){
        this.lastTransactionAmount = lastTransactionAmount;
    }

    public void setTransactions(ArrayList<Transaction> transactions){
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

    public ArrayList<Transaction> getTransactions(){
        return this.transactions;
    }

}