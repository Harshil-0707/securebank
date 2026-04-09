package com.harshil.bank.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

import com.harshil.bank.dto.SignUpData;
import com.harshil.bank.dto.ServiceResponse;

import com.harshil.bank.service.BankService;

import com.harshil.bank.model.User;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet{

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        req.getRequestDispatcher("/signup.html").forward(req,res);

    }

    @Override
    protected void doPost(HttpServletRequest req,HttpServletResponse res)
            throws ServletException , IOException {

        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();

        String line;

        while((line = reader.readLine()) != null){
            sb.append(line);
        }

        String json = sb.toString();

        SignUpData sd = SignupServlet.mapper.readValue(json,SignUpData.class);

        BankService bs = new BankService();

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        
        ServiceResponse<User> responseObj = bs.createUser(sd);

        if(responseObj.isSuccess()){
            HttpSession session = req.getSession();
            session.setAttribute("userId", responseObj.getData().getId());

            res.getWriter().write("{\"message\":\"Success\"}");
        }else{
            res.getWriter().write("{\"message\":\"" + responseObj.getMessage() + "\"}");
        }
    }
}