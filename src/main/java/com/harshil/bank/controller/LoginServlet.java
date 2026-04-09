package com.harshil.bank.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

import com.harshil.bank.dto.LoginData;
import com.harshil.bank.service.BankService;

import com.harshil.bank.model.User;
import com.harshil.bank.model.Account;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        req.getRequestDispatcher("/index.html").forward(req, res);
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

        LoginData ld = LoginServlet.mapper.readValue(json,LoginData.class);

        BankService bs = new BankService();

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        PrintWriter out = res.getWriter();
        User user = bs.UserExists(ld);

        if(user != null){
            Account account = bs.getAccountData(user.getId());
            if(account != null){
                HttpSession session = req.getSession();
                session.setAttribute("userId", user.getId());
                session.setAttribute("accountNumber",account.getAccountNumber());

                out.print("{\"message\":\"Success\"}");
            }else{
                out.print("{\"message\":\"Failed\"}");
            }
        }else{
            out.print("{\"message\":\"Failed\"}");
        }
        out.flush();

    }
}