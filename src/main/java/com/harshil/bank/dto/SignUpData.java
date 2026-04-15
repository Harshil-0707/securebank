package com.harshil.bank.dto;

public class SignUpData{

    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    public SignUpData(){}

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