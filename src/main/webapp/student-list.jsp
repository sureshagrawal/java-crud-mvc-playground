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

<%
    int currentPage  = (Integer) request.getAttribute("currentPage");
    int totalPages   = (Integer) request.getAttribute("totalPages");
    int totalRecords = (Integer) request.getAttribute("totalRecords");
    int pageSize     = (Integer) request.getAttribute("pageSize");

    int start = (currentPage - 1) * pageSize + 1;
    int end   = Math.min(start + pageSize - 1, totalRecords);
%>

<!-- TOP TOOLBAR -->
<div class="d-flex align-items-center justify-content-between mb-2 flex-wrap">

    <!-- 1️⃣ RECORD INFO -->
    <div class="text-muted me-4">
        Showing
        <strong>
            <%= start %>
            <i class="fa-solid fa-arrow-right mx-1 text-secondary"></i>
            <%= end %>
        </strong>
        of <strong><%= totalRecords %></strong>
    </div>

    <!-- 2️⃣ PAGE SIZE -->
    <form action="students" method="get"
          class="d-flex align-items-center mb-0 me-4">

        <span class="me-2 text-muted">Show</span>

        <select name="pageSize"
                class="form-select form-select-sm me-2"
                style="width:80px;"
                onchange="this.form.submit()">

            <option value="5"  <%= pageSize == 5  ? "selected" : "" %>>5</option>
            <option value="10" <%= pageSize == 10 ? "selected" : "" %>>10</option>
            <option value="20" <%= pageSize == 20 ? "selected" : "" %>>20</option>
            <option value="50" <%= pageSize == 50 ? "selected" : "" %>>50</option>
        </select>

        <span class="text-muted">entries</span>
    </form>

    <!-- 3️⃣ GO TO PAGE -->
    <form action="students" method="get"
          class="d-flex align-items-center mb-0">

        <span class="me-2 text-muted">Go to page</span>

        <input type="number"
               name="page"
               min="1"
               max="<%= totalPages %>"
               class="form-control form-control-sm me-2"
               style="width:70px;"
               required>

        <button class="btn btn-sm btn-primary">
            Go
        </button>
    </form>
</div>

    <!-- 4️⃣ PAGINATION -->
    <% if (totalPages > 1) { %>
        <nav>
            <ul class="pagination justify-content-center mb-0">

                <!-- FIRST -->
                <li class="page-item <%= (currentPage == 1 ? "disabled" : "") %>">
                    <a class="page-link" href="students?page=1">
                        <i class="fa-solid fa-backward-fast"></i>
                    </a>
                </li>

                <!-- PREVIOUS -->
                <li class="page-item <%= (currentPage == 1 ? "disabled" : "") %>">
                    <a class="page-link"
                       href="students?page=<%= currentPage - 1 %>">
                        <i class="fa-solid fa-chevron-left"></i>
                    </a>
                </li>

                <!-- PAGE NUMBERS -->
                <% for (int i = 1; i <= totalPages; i++) { %>
                    <li class="page-item <%= (i == currentPage ? "active" : "") %>">
                        <a class="page-link"
                           href="students?page=<%= i %>">
                            <%= i %>
                        </a>
                    </li>
                <% } %>

                <!-- NEXT -->
                <li class="page-item <%= (currentPage == totalPages ? "disabled" : "") %>">
                    <a class="page-link"
                       href="students?page=<%= currentPage + 1 %>">
                        <i class="fa-solid fa-chevron-right"></i>
                    </a>
                </li>

                <!-- LAST -->
                <li class="page-item <%= (currentPage == totalPages ? "disabled" : "") %>">
                    <a class="page-link"
                       href="students?page=<%= totalPages %>">
                        <i class="fa-solid fa-forward-fast"></i>
                    </a>
                </li>

            </ul>
        </nav>
    <% } %>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
