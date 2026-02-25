package com.harshil.bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction{

    private int transactionId;
    private String accountNumber;
    private String transactionType;
    private BigDecimal transactionAmount;
    private LocalDateTime transactionTime;


    public Transaction(String accountNumber,String transactionType,BigDecimal transactionAmount){
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
    }
    
    // setter

    public void setTransactionId(int transactionId){
        this.transactionId = transactionId;
    }

    public void setAccountNumber(String accountNumber){
        this.accountNumber = accountNumber;
    }

    public void setTransactionType(String transactionType){
        this.transactionType = transactionType;
    }

    public void setTransactionAmount(BigDecimal transactionAmount){
        this.transactionAmount = transactionAmount;
    }

    public void setTransactionTime(LocalDateTime transactionTime){
        this.transactionAmount = transactionAmount;
    }

    // getter

    public int getTransactionId(){
        return this.transactionId;
    }

    public String getAccountNumber(){
        return this.accountNumber;
    }

    public String getTransactionType(){
        return this.transactionType;
    }

    public BigDecimal getTransactionAmount(){
        return this.transactionAmount;
    }

    public LocalDateTime getTransactionTime(){
        return this.transactionTime;
    }

}