package com.harshil.bank.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.harshil.bank.dto.TransactionData;
import com.harshil.bank.service.BankService;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/bank/dashboard/deposit/api/")
public class DepositServlet extends HttpServlet{

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        HttpSession session = req.getSession(false);

        Integer userIdObj = (Integer) session.getAttribute("userId");
        String accountNumber = (String) session.getAttribute("accountNumber");

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        if(session == null || userIdObj == null || accountNumber == null){
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int userId = userIdObj;

        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();

        String line;

        while((line = reader.readLine()) != null){
            sb.append(line);
        }

        String json = sb.toString();

        BankService bs = new BankService();

        TransactionData transactionData = DepositServlet.mapper.readValue(json,TransactionData.class);

        transactionData.setUserId(userId);

        String error = bs.deposit(transactionData);

        if(error == null){
            res.getWriter().print("{\"message\":\"Success\"}");
        }else{
            res.getWriter().print("{\"message\":\"" + error + "\"}");
        }

    }

}