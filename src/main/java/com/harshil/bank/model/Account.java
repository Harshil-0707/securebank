package com.harshil.bank.model;

import java.util.UUID;
import java.math.BigDecimal;

public class Account{
    private int accountId;
    private int userId;
    private String accountNumber;
    private BigDecimal balance;
    private String accountType;

    public Account(int userId,String accountType){
        this.accountNumber = UUID.randomUUID().toString().substring(0,6);
        this.userId = userId;
        this.accountType = accountType;
    }

    // setter

    public void setAccountId(int accountId){
        this.accountId = accountId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public void setAccountNumber(String accountNumber){
        this.accountNumber = accountNumber;
    }

    public void setBalance(BigDecimal balance){
        this.balance = balance;
    }

    public void setAccountType(String accountType){
        this.accountType = accountType;
    }

    // getter

    public int getAccountId(){
        return this.accountId;
    }

    public int getUserId(){
        return this.userId;
    }

    public String getAccountNumber(){
        return this.accountNumber;
    }

    public BigDecimal getBalance(){
        return this.balance;
    }

    public String getAccountType(){
        return this.accountType;
    }

}