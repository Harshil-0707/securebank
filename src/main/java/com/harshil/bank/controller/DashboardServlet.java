package com.harshil.bank.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

import java.math.BigDecimal;

import com.harshil.bank.dto.DashboardData;

import com.harshil.bank.dao.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/bank/dashboard/api/")
public class DashboardServlet extends HttpServlet{

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        DashboardData dashboardData = new DashboardData("Harshil",new BigDecimal(2000),new BigDecimal(2000.12));
        res.getWriter().print(DashboardServlet.mapper.writeValueAsString(dashboardData));

    }
}