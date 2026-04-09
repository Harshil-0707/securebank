package com.harshil.bank.dto;

public class ServiceResponse<T>{
    private boolean success;
    private String message;
    private T data;

    public ServiceResponse(boolean success,String message,T data){
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess(){return this.success;}
    public String getMessage(){return this.message;}
    public T getData(){return this.data;}

}