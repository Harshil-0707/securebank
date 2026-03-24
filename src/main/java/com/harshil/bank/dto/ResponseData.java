package com.harshil.bank.dto;

public class ResponseData{

    public String message;
    public int status;

    public ResponseData(String message,int status){
        this.message = message;
        this.status = status;
    }
}