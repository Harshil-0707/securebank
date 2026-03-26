package com.harshil.bank.dto;

public class ResponseData{

    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    public ResponseData(){}

    // setters

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    // getters

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public String getPassword(){
        return this.password;
    }

}