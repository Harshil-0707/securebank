package com.harshil.bank.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

import com.harshil.bank.dto.ResponseData;
import com.harshil.bank.service.BankService;

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

        ResponseData data = SignupServlet.mapper.readValue(json,ResponseData.class);

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        PrintWriter out = res.getWriter();
        out.print("{\"message\":\"Success\"}");
        out.flush();

    }
}