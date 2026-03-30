package com.harshil.bank.dto;

public class LoginData{

    private String email;
    private String password;

    public LoginData(){}

    // setters

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    // getters

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

}