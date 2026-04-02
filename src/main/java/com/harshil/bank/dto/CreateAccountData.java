package com.harshil.bank.dto;

import java.math.BigDecimal;

public class CreateAccountData{
    
    private BigDecimal balance;
    private String accountType;
    private int userId;

    public CreateAccountData(int userId,String accountType,BigDecimal balance){
        this.userId = userId;
        this.accountType = accountType;
        this.balance = balance;
    }

    // setter

    public void setBalance(BigDecimal balance){
        this.balance = balance;
    }

    public void setAccountType(String accountType){
        this.accountType = accountType;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    // getter

    public BigDecimal getBalance(){
        return this.balance;
    }

    public String getAccountType(){
        return this.accountType;
    }

    public int getUserId(){
        return this.userId;
    }

}