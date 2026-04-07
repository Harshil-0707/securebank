package com.harshil.bank.dao;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.harshil.bank.model.User;

public class UserDAO{
    
    private Connection con = null;
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    public UserDAO(Connection con){
        this.con = con;
    }

    public User createUser(User user){
        String sql = "INSERT INTO users (name,email,phone,password) VALUES (?,?,?,?)";
        int rowsAffected = 0;

        try(PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,user.getName());
            ps.setString(2,user.getEmail());
            ps.setString(3,user.getPhoneNumber());
            ps.setString(4,user.getPassword());
            rowsAffected = ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){  
                if(rs.next()){
                    User u = new User();
                    u.setId(rs.getInt(1));
                    return u;    
                }
            }catch(Exception e){
                UserDAO.logger.error("Error getting user id: " , e);
            }
        }catch(Exception e){
            UserDAO.logger.error("Error inserting user into database: " , e);
        }
        return null;
    }

    public boolean userExists(String email,String password){
        String sql = "SELECT 1 FROM users WHERE email = ? AND password = ?";
        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1,email);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }catch(Exception e){
            UserDAO.logger.error("Error logging in user",e);
            e.printStackTrace();
        }
        return false;
    }

    public User getUserData(int id){
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1,id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return new User(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("password")
                    );
                }
            }
        }catch(Exception e){
            UserDAO.logger.error("Error getting user by id = {} ",id,e);
        }
        return null;
    }

    public boolean validateUserID(int id){
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }catch(Exception e){
            UserDAO.logger.error("Error getting user id ={} ",id,e);
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
            UserDAO.logger.error("Error getting user by field name = {} and value = {}",fieldName,value,e);
            e.printStackTrace();
        }
        return false;
    }

}