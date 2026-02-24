<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.nsgacademy.crudmvc.model.Student" %>
<%@ page import="com.nsgacademy.crudmvc.auth.model.User" %> <%-- 🔥 NEW: import User --%>

<!doctype html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Student Form</title>

<link rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css">

</head>

<body>
<div class="container my-5">

    <%
        User loggedUser = (User) session.getAttribute("user"); // 🔥 NEW
        if (loggedUser == null) { // 🔥 NEW
            response.sendRedirect("login.jsp"); // 🔥 NEW
            return; // 🔥 NEW
        }
    %>

    <h2 class="text-center mb-3">MVC CRUD Application</h2>
    <h5 class="text-center mb-4">JSP + Servlet + PostgreSQL</h5>

    <!-- 🔥 NEW: welcome + logout -->
    <div class="text-end mb-3">
        Welcome <strong><%= loggedUser.getName() %></strong> |
        <a href="logout" class="text-danger">Logout</a>
    </div>

    <%
        Student student = (Student) request.getAttribute("student");
        boolean isEdit = (student != null);
    %>

    <h4><%= isEdit ? "Edit Student" : "Add New Student" %></h4>

    <form action="students?action=<%= isEdit ? "update" : "insert" %>" method="post">

        <% if (isEdit) { %>
            <input type="hidden" name="id" value="<%= student.getId() %>">
        <% } %>

        <!-- NAME -->
        <div class="mb-3">
            <label class="form-label">Name</label>
            <input type="text" name="name" class="form-control"
                   placeholder="Enter your name"
                   value="<%=
                       (request.getParameter("name") != null)
                       ? request.getParameter("name")
                       : (student != null ? student.getName() : "")
                   %>"
                   required
                   pattern="[A-Za-z ]{3,50}"
                   title="Name must be 3-50 characters, letters and spaces only.">
            <p class="text-danger">
                <%= request.getAttribute("nameError") != null ? request.getAttribute("nameError") : "" %>
            </p>
        </div>

        <!-- EMAIL -->
        <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" name="email" class="form-control"
                   placeholder="Enter your email"
                   value="<%=
                       (request.getParameter("email") != null)
                       ? request.getParameter("email")
                       : (student != null ? student.getEmail() : "")
                   %>"
                   required>
            <p class="text-danger">
                <%= request.getAttribute("emailError") != null ? request.getAttribute("emailError") : "" %>
            </p>
        </div>

        <!-- MOBILE -->
        <div class="mb-3">
            <label class="form-label">Mobile</label>
            <input type="text" name="mobile" class="form-control"
                   placeholder="Enter your mobile number"
                   value="<%=
                       (request.getParameter("mobile") != null)
                       ? request.getParameter("mobile")
                       : (student != null ? student.getMobile() : "")
                   %>"
                   required
                   pattern="[0-9]{10}"
                   title="Mobile number must be exactly 10 digits.">
            <p class="text-danger">
                <%= request.getAttribute("mobileError") != null ? request.getAttribute("mobileError") : "" %>
            </p>
        </div>

        <button type="submit" class="btn btn-success">Save</button>
        <a href="students?action=list" class="btn btn-secondary">Cancel</a>

    </form>
</div>

<script
  src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
