package com.nsgacademy.crudmvc.web;

import com.nsgacademy.crudmvc.dao.StudentDAO;
import com.nsgacademy.crudmvc.exception.DAOException;
import com.nsgacademy.crudmvc.model.Pagination;
import com.nsgacademy.crudmvc.model.Student;

import com.nsgacademy.crudmvc.model.StudentFilter;
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

        // 1. Read page parameter
        // 2. Build Pagination
        // 3. Build empty StudentFilter
        // 4. Call countStudents(filter)
        // 5. Call listStudents(filter, pagination)
        // 6. Set attributes
        // 7. Forward to JSP

        int page = 1;
        int pageSize = 5; // default

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        if (request.getParameter("pageSize") != null) {
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        }


        Pagination pagination = new Pagination(page, pageSize);                     // 2. Build Pagination

        StudentFilter filter = new StudentFilter(); // empty for Phase-1            // 3. Build empty StudentFilter
        int totalRecords = studentDAO.countStudents(filter);                        // 4. Call countStudents(filter)
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

//        if (page < 1)
//            page = 1;
//        if (page > totalPages && totalPages > 0)
//            page = totalPages;
//        pagination.setPage(page);

        List<Student> students = studentDAO.listStudents(filter, pagination);       // 5. Call listStudents(filter, pagination)

        request.setAttribute("students", students);
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalRecords", totalRecords);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("student-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("student-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Student student = studentDAO.getStudentById(id);
        request.setAttribute("student", student);
        request.getRequestDispatcher("student-form.jsp").forward(request, response);
    }

    private void insertStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");

        if (!validate(request, name, email, mobile)) {
            request.getRequestDispatcher("student-form.jsp").forward(request, response);
            return;
        }

        studentDAO.insert(new Student(name.trim(), email.trim(), mobile.trim()));
        response.sendRedirect("students?action=list&success=Added Successfully");
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

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

        studentDAO.update(new Student(id, name.trim(), email.trim(), mobile.trim()));
        response.sendRedirect("students?action=list&success=Updated Successfully");
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        studentDAO.delete(id);
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
