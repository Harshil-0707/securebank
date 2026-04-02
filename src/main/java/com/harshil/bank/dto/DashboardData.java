package com.harshil.bank.dto;

import java.math.BigDecimal;


public class DashboardData{

    private String name;
    private BigDecimal balance;
    private BigDecimal lastTransactionAmount;

    public DashboardData(){}

    public DashboardData(String name,BigDecimal balance,BigDecimal lastTransactionAmount){
        this.name = name;
        this.balance = balance;
        this.lastTransactionAmount = lastTransactionAmount;
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

}