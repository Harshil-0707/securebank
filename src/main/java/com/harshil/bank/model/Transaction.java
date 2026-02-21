package com.harshil.bank.model;

import java.time.LocalDateTime;

public class Transaction{

    private int transactionId;
    private int accountNumber;
    private String transactionType;
    private double transactionAmount;
    private LocalDateTime transactionTime;


    public Transaction(int accountNumber,String transactionType,double transactionAmount,LocalDateTime transactionTime){
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.transactionTime = transactionTime;
    }
    
    // setter

    public void setTransactionId(int transactionId){
        this.transactionId = transactionId;
    }

    public void setAccountNumber(int accountNumber){
        this.accountNumber = accountNumber;
    }

    public void setTransactionType(String transactionType){
        this.transactionType = transactionType;
    }

    public void setTransactionAmount(double transactionAmount){
        this.transactionAmount = transactionAmount;
    }

    public void setTransactionTime(LocalDateTime transactionTime){
        this.transactionAmount = transactionAmount;
    }

    // getter

    public int getTransactionId(){
        return this.transactionId;
    }

    public int getAccountNumber(){
        return this.accountNumber;
    }

    public String getTransactionType(){
        return this.transactionType;
    }

    public double getTransactionAmount(){
        return this.transactionAmount;
    }

    public LocalDateTime getTransactionTime(){
        return this.transactionTime;
    }

}