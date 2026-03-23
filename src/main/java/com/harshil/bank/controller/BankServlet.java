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

        switch (path) {

            case "/dashboard":
                req.getRequestDispatcher("/dashboard.html").forward(req, res);
                break;

            case "/view-balance":
                res.getWriter().println("Viewing balance");
                break;

            case "/transfer":
                res.getWriter().println("Transfer");
                break;

            case "/transactions":
                res.getWriter().println("Transactions");
                break;

            default:
                res.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid route");
        }
    }
}