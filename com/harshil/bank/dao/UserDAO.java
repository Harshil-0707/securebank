package com.harshil.bank.dao;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.time.LocalDateTime;

import com.harshil.bank.model.User;

public class UserDAO{
    
    Connection con = null;

    public UserDAO(Connection con){
        this.con = con;
    }

    public boolean createUser(User user){
        String sql = "INSERT INTO users (name,email,phone) VALUES (?,?,?)";
        int rowsAffected = 0;

        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(user.getName());
            ps.setString(user.getemail());
            ps.setString(user.getPhoneNumber());
            rowsAffected = ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
        return rowsAffected > 0;
    }

   
    public boolean existsBy(String fieldName,String value){
        // TODO: Change table name in this query
        String sql = "SELECT 1 FROM users WHERE " + fieldName + " = ?";
        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1,value);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

}