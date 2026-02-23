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

        try(PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,user.getName());
            ps.setString(2,user.getEmail());
            ps.setString(3,user.getPhoneNumber());
            rowsAffected = ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){
                if(rs.next()) System.out.println("User ID: " + rs.getInt(1));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return rowsAffected > 0;
    }

    public boolean validateUserID(int id){
        String sql = "SELECT * FROM users WHERE id = ?";
        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setId(1,id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsBy(String fieldName,String value){
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