package com.harshil.bank.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

import com.harshil.bank.dto.CreateAccountData;

import com.harshil.bank.service.BankService;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/create-account")
public class CreateAccountServlet extends HttpServlet{

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        req.getRequestDispatcher("/create-account.html").forward(req,res);                
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

        CreateAccountData cad = CreateAccountServlet.mapper.readValue(json,CreateAccountData.class);

        BankService bs = new BankService();

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        PrintWriter out = res.getWriter();

        if(bs.createAccount(cad)){
            out.print("{\"message\":\"Success\"}");
        }else{
            out.print("{\"message\":\"Failed\"}");
        }
        out.flush();

    }
}