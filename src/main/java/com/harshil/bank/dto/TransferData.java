package com.harshil.bank.dto;

import java.math.BigDecimal;

public class TransferData {

    private int userId;
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private BigDecimal amount;

    public TransferData(){}

    public void setUserId(int userId){
        this.userId = userId;
    }

    public void setSenderAccountNumber(String senderAccountNumber){
        this.senderAccountNumber = senderAccountNumber;
    }

    public void setReceiverAccountNumber(String receiverAccountNumber){
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public void setAmount(BigDecimal amount){
        this.amount = amount;
    }

    public int getUserId(){
        return userId;
    }

    public String getSenderAccountNumber(){
        return senderAccountNumber;
    }

    public String getReceiverAccountNumber(){
        return receiverAccountNumber;
    }

    public BigDecimal getAmount(){
        return amount;
    }
}