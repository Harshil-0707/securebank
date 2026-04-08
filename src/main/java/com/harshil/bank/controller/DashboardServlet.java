package com.harshil.bank.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

import java.math.BigDecimal;

import com.harshil.bank.dto.DashboardData;

import com.harshil.bank.dao.*;

import com.harshil.bank.service.BankService;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/bank/dashboard/api/")
public class DashboardServlet extends HttpServlet{

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        Integer userIdObj = (Integer) session.getAttribute("userId");
        String accountNumber = (String) session.getAttribute("accountNumber");

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        if(session == null || userIdObj == null || accountNumber == null){
            res.getWriter().write("{\"message\":\"Failed\"}");
            return;
        }

        int userId = userIdObj;

        BankService bs = new BankService();

        DashboardData dashboardData = bs.getDashboardData(userId,accountNumber);
        
        res.getWriter().print(DashboardServlet.mapper.writeValueAsString(dashboardData));

    }
}