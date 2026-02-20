package com.harshil.bank.model;

import java.time.LocalDateTime;

public class User{

    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDateTime createdAt;

    public User(String name,String email,String phoneNumber){
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // setters

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    // getters

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    } 

    public String getEmail(){
        return this.email;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public LocalDateTime getCreatedAt(){
        return this.createdAt;
    }

}