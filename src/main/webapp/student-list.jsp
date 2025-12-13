<%@ page import="java.util.*, com.nsgacademy.crudmvc.model.Student" %>
<!DOCTYPE html>
<html>
<head>
    <title>MVC CRUD Application</title>

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css">

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"/>
</head>

<body class="container mt-4">

<h2 class="text-center">MVC CRUD Application</h2>
<h5 class="text-center mb-4">JSP + Servlet + PostgreSQL</h5>

<%
    String success = request.getParameter("success");
    if (success != null) {
%>
    <div class="alert alert-success text-center"><%= success %></div>
<%
    }
%>

<!-- ADD STUDENT -->
<a href="students?action=new" class="btn btn-primary mb-3">
    <i class="fa-solid fa-user-plus me-1"></i> Add Student
</a>

<table class="table table-bordered table-striped">
    <thead class="table-dark">
    <tr>
        <th>#</th>
        <th>Name</th>
        <th>Email</th>
        <th>Mobile</th>
        <th>Action</th>
    </tr>
    </thead>

    <tbody>
    <%
        List<Student> students = (List<Student>) request.getAttribute("students");
        int cnt = 1;

        if (students != null && !students.isEmpty()) {
            for (Student s : students) {
    %>
        <tr>
            <td><%= cnt++ %></td>
            <td><%= s.getName() %></td>
            <td><%= s.getEmail() %></td>
            <td><%= s.getMobile() %></td>
            <td>
                <a href="students?action=edit&id=<%= s.getId() %>"
                   class="btn btn-warning btn-sm"
                   title="Edit">
                    <i class="fa-solid fa-pen-to-square"></i>
                </a>

                <a href="students?action=delete&id=<%= s.getId() %>"
                   class="btn btn-danger btn-sm"
                   onclick="return confirm('Are you sure you want to delete this student?');"
                   title="Delete">
                    <i class="fa-solid fa-trash"></i>
                </a>
            </td>
        </tr>
    <%
            }
        } else {
    %>
        <tr>
            <td colspan="5" class="text-center">No students found.</td>
        </tr>
    <%
        }
    %>
    </tbody>
</table>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
