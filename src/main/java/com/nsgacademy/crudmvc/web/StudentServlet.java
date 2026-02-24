package com.nsgacademy.crudmvc.web;

import com.nsgacademy.crudmvc.dao.StudentDAO;
import com.nsgacademy.crudmvc.exception.DAOException;
import com.nsgacademy.crudmvc.model.Pagination;
import com.nsgacademy.crudmvc.model.Student;

import com.nsgacademy.crudmvc.model.StudentFilter;
import com.nsgacademy.crudmvc.auth.model.User; // 🔥 NEW import for session user

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {"/", "/students"})
public class StudentServlet extends HttpServlet {

    private StudentDAO studentDAO;

    @Override
    public void init() {
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "new": showNewForm(request, response); break;
                case "insert": insertStudent(request, response); break;
                case "edit": showEditForm(request, response); break;
                case "update": updateStudent(request, response); break;
                case "delete": deleteStudent(request, response); break;
                default: listStudents(request, response);
            }
        } catch (DAOException e) {

            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("errorException", e);
            request.setAttribute("rootCause", e.getCause());

            e.printStackTrace();
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int page = 1;
        int pageSize = 5; // default
        String search = "";
        String sortBy = "id";
        String sortDir = "asc";

        User user = (User) request.getSession().getAttribute("user"); // 🔥 get logged-in user
        int userId = user.getId(); // 🔥 extract userId

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        if (request.getParameter("pageSize") != null) {
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        }

        Pagination pagination = new Pagination(page, pageSize);

        if (request.getParameter("search") != null) {
            search = request.getParameter("search");
        }

        if (request.getParameter("sortBy") != null) {
            sortBy = request.getParameter("sortBy");
        }

        if (request.getParameter("sortDir") != null) {
            sortDir = request.getParameter("sortDir");
        }

        StudentFilter filter = new StudentFilter();
        filter.setSearch(search);
        filter.setSortBy(sortBy);
        filter.setSortDir(sortDir);

        int totalRecords = studentDAO.countStudents(filter, userId); // 🔥 pass userId
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        if (page < 1)
            page = 1;
        if (page > totalPages && totalPages > 0)
            page = totalPages;
        pagination.setPage(page);

        List<Student> students = studentDAO.listStudents(filter, pagination, userId); // 🔥 pass userId

        request.setAttribute("students", students);
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalRecords", totalRecords);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("search", search);
        request.setAttribute("sortBy", filter.getSortBy());
        request.setAttribute("sortDir", filter.getSortDir());

        request.getRequestDispatcher("student-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("student-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user"); // 🔥
        int userId = user.getId(); // 🔥

        int id = Integer.parseInt(request.getParameter("id"));
        Student student = studentDAO.getStudentById(id, userId); // 🔥 pass userId
        request.setAttribute("student", student);
        request.getRequestDispatcher("student-form.jsp").forward(request, response);
    }

    private void insertStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        User user = (User) request.getSession().getAttribute("user"); // 🔥
        int userId = user.getId(); // 🔥

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");

        if (!validate(request, name, email, mobile)) {
            request.getRequestDispatcher("student-form.jsp").forward(request, response);
            return;
        }

        studentDAO.insert(new Student(name.trim(), email.trim(), mobile.trim()), userId); // 🔥 pass userId
        response.sendRedirect("students?action=list&success=Added Successfully");
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        User user = (User) request.getSession().getAttribute("user"); // 🔥
        int userId = user.getId(); // 🔥

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");

        if (!validate(request, name, email, mobile)) {
            request.setAttribute("student",
                    new Student(id, name.trim(), email.trim(), mobile.trim()));
            request.getRequestDispatcher("student-form.jsp").forward(request, response);
            return;
        }

        studentDAO.update(new Student(id, name.trim(), email.trim(), mobile.trim()), userId); // 🔥 pass userId
        response.sendRedirect("students?action=list&success=Updated Successfully");
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        User user = (User) request.getSession().getAttribute("user"); // 🔥
        int userId = user.getId(); // 🔥

        int id = Integer.parseInt(request.getParameter("id"));
        studentDAO.delete(id, userId); // 🔥 pass userId
        response.sendRedirect("students?action=list&success=Deleted Successfully");
    }

    private boolean validate(HttpServletRequest request, String name, String email, String mobile) {

        boolean valid = true;

        if (name == null || !Pattern.matches("^[A-Za-z ]{3,50}$", name.trim())) {
            valid = false;
            request.setAttribute("nameError",
                    "Name must be 3-50 alphabetic characters.");
        }

        if (email == null ||
                !Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email.trim())) {
            valid = false;
            request.setAttribute("emailError", "Invalid email.");
        }

        if (mobile == null || !Pattern.matches("^[0-9]{10}$", mobile.trim())) {
            valid = false;
            request.setAttribute("mobileError", "Mobile must be 10 digits.");
        }

        return valid;
    }
}
