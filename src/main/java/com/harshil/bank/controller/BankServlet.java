package com.harshil.bank.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

@WebServlet("/bank/*")
public class BankServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String path = req.getPathInfo();

        res.setContentType("text/html");
        
        if (path == null) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Path missing");
            return;
        }

        HttpSession session = req.getSession(false);
       
        if(session == null || session.getAttribute("userId") == null || session.getAttribute("accountNumber") == null){
            res.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }

        switch (path) {
            case "/dashboard":
            case "/dashboard/":
                req.getRequestDispatcher("/dashboard.html").forward(req, res);
                break;

            case "/dashboard/view-balance":
                req.getRequestDispatcher("/viewBalance.html").forward(req, res);
                break;

            case "/dashboard/transfer":
                req.getRequestDispatcher("/transfer.html").forward(req, res);
                break;

            case "/dashboard/transactions":
                req.getRequestDispatcher("/transactions.html").forward(req, res);
                break;

            default:
                res.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid route");
        }
    }
}