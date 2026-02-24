<%@ page isErrorPage="true" %>
<%@ page import="com.nsgacademy.crudmvc.auth.model.User" %> <%-- 🔥 NEW: import User --%>

<!DOCTYPE html>
<html>
<head>
<title>Error Occurred</title>
<link rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css">
</head>

<body class="container my-5">

<%
    User loggedUser = (User) session.getAttribute("user"); // 🔥 NEW
%>

<!-- 🔥 NEW: show user info only if logged in -->
<% if (loggedUser != null) { %>
    <div class="text-end mb-3">
        Welcome <strong><%= loggedUser.getName() %></strong> |
        <a href="logout" class="text-danger">Logout</a>
    </div>
<% } %>

<h2 class="text-danger">Something went wrong!</h2>
<p class="text-muted">We encountered an issue processing your request.</p>

<div class="card mt-4">
    <div class="card-header bg-dark text-white">
        Developer Debug Information
    </div>
    <div class="card-body bg-light">

        <p><strong>Error Message:</strong></p>
        <pre><%= request.getAttribute("errorMessage") %></pre>

        <%
            Throwable ex = (Throwable) request.getAttribute("errorException");
            Throwable cause = (Throwable) request.getAttribute("rootCause");
        %>

        <% if (cause != null) { %>
            <p><strong>Cause:</strong></p>
            <pre><%= cause.toString() %></pre>
        <% } else if (ex != null) { %>
            <p><strong>Exception:</strong></p>
            <pre><%= ex.toString() %></pre>
        <% } else { %>
            <pre>No technical error details available</pre>
        <% } %>

    </div>
</div>

<a href="students?action=list" class="btn btn-primary mt-3">Back to Home</a>

</body>
</html>
