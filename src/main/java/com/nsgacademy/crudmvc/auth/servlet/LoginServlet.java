package com.nsgacademy.crudmvc.auth.servlet;

import com.nsgacademy.crudmvc.auth.dao.UserDAO;
import com.nsgacademy.crudmvc.auth.model.User;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserDAO dao = new UserDAO();
        User user = dao.login(email, password);

        if(user != null){
            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            resp.sendRedirect("students");
        } else {
            resp.sendRedirect("login.jsp?error=true");
        }
    }
}
