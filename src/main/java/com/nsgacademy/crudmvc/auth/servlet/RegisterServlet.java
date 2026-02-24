package com.nsgacademy.crudmvc.auth.servlet;

import com.nsgacademy.crudmvc.auth.dao.UserDAO;
import com.nsgacademy.crudmvc.auth.model.User;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        User user = new User(
                req.getParameter("name"),
                req.getParameter("email"),
                req.getParameter("password")
        );

        new UserDAO().register(user);

        resp.sendRedirect("login.jsp");
    }
}
