package com.harshil.bank.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;

import com.harshil.bank.model.Transaction;
import com.harshil.bank.service.BankService;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/bank/dashboard/transactions/api/")
public class TransactionsServlet extends HttpServlet{

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
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

        BankService bs = new BankService();

        ArrayList<Transaction> transactions = bs.getAllTransactions(userId,accountNumber);

        res.getWriter().print(TransactionsServlet.mapper.writeValueAsString(transactions));

    }

}