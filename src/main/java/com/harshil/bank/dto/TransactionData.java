package com.harshil.bank.dto;

import java.math.BigDecimal;

public class TransactionData{

    private int userId;
    private String accountNumber;
    private BigDecimal amount;

    public TransactionData(){}

    // setter

    public void setAccountNumber(String accountNumber){
        this.accountNumber = accountNumber;
    }

    public void setAmount(BigDecimal amount){
        this.amount = amount;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    //getter

    public String getAccountNumber(){
        return this.accountNumber;
    }

    public BigDecimal getAmount(){
        return this.amount;
    }

    public int getUserId(){
        return this.userId;
    }

}