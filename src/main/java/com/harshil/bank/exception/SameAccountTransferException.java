package com.harshil.bank.exception;

public class SameAccountTransferException extends Exception{
    public SameAccountTransferException(String message){
        super(message);
    }
}