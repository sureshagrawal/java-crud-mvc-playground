package com.nsgacademy.crudmvc.dao;

import com.nsgacademy.crudmvc.exception.DAOException;
import com.nsgacademy.crudmvc.model.Pagination;
import com.nsgacademy.crudmvc.model.Student;
import com.nsgacademy.crudmvc.model.StudentFilter;
import com.nsgacademy.crudmvc.utils.JDBCUtils;

import java.sql.*;
import java.util.*;

public class StudentDAO {

    private static final String INSERT_SQL =
            "INSERT INTO students (name, email, mobile, user_id) VALUES (?, ?, ?, ?)"; // 🔥 added user_id

//    private static final String SELECT_ALL_SQL =
//            "SELECT * FROM students ORDER BY id";

    private static final String BASE_SELECT_SQL =
            "SELECT id, name, email, mobile FROM students WHERE user_id=?"; // 🔥 filter by user

    private static final String PAGINATION_SQL =
            " LIMIT ? OFFSET ?";

    private static final String COUNT_SQL =
            "SELECT COUNT(*) FROM students WHERE user_id=?"; // 🔥 filter by user

    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM students WHERE id=? AND user_id=?"; // 🔥 filter by user

    private static final String UPDATE_SQL =
            "UPDATE students SET name=?, email=?, mobile=? WHERE id=? AND user_id=?"; // 🔥 filter by user

    private static final String DELETE_SQL =
            "DELETE FROM students WHERE id=? AND user_id=?"; // 🔥 filter by user


    // =================================================
    // INSERT
    // =================================================
    public void insert(Student student, int userId) { // 🔥 added userId param
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {

            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getMobile());
            stmt.setInt(4, userId); // 🔥 set user_id

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Unable to insert student", e);
        }
    }


    // =================================================
    // LIST
    // =================================================
    public List<Student> listStudents(StudentFilter filter, Pagination pagination, int userId) { // 🔥 added userId param

        List<Student> studentList = new ArrayList<>();

        String sql = BASE_SELECT_SQL;

        boolean hasSearch = filter != null && filter.hasSearch();

        if (hasSearch) {
            sql += " AND (name ILIKE ? OR email ILIKE ? OR mobile ILIKE ?)"; // 🔥 changed WHERE → AND
        }

        // 🔥 SORTING
        sql += " ORDER BY " + filter.getSortBy() + " " + filter.getSortDir();

        // 🔚 PAGINATION ALWAYS LAST
        sql += PAGINATION_SQL;

        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int index = 1;

            ps.setInt(index++, userId); // 🔥 set user_id first

            if (hasSearch) {
                String keyword = "%" + filter.getSearch().trim() + "%";
                ps.setString(index++, keyword);
                ps.setString(index++, keyword);
                ps.setString(index++, keyword);
            }

            ps.setInt(index++, pagination.getPageSize());
            ps.setInt(index, pagination.getOffset());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    studentList.add(new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("mobile")
                    ));
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Failed to list students with pagination", e);
        }

        return studentList;
    }


    // =================================================
    // COUNT
    // =================================================
    public int countStudents(StudentFilter filter, int userId) { // 🔥 added userId param

        String sql = COUNT_SQL;

        boolean hasSearch = filter != null && filter.hasSearch();

        if (hasSearch) {
            sql += " AND (name ILIKE ? OR email ILIKE ? OR mobile ILIKE ?)"; // 🔥 changed WHERE → AND
        }

        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int index = 1;

            ps.setInt(index++, userId); // 🔥 set user_id first

            if (hasSearch) {
                String keyword = "%" + filter.getSearch().trim() + "%";
                ps.setString(index++, keyword);
                ps.setString(index++, keyword);
                ps.setString(index++, keyword);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new DAOException("Failed to count students", e);
        }

        return 0;
    }


    // =================================================
    // GET BY ID
    // =================================================
    public Student getStudentById(int id, int userId) { // 🔥 added userId param
        Student student = null;

        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setInt(1, id);
            stmt.setInt(2, userId); // 🔥 set user_id

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    student = new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("mobile")
                    );
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Unable to fetch student by id", e);
        }
        return student;
    }


    // =================================================
    // UPDATE
    // =================================================
    public void update(Student student, int userId) { // 🔥 added userId param
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getMobile());
            stmt.setInt(4, student.getId());
            stmt.setInt(5, userId); // 🔥 set user_id

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Unable to update student", e);
        }
    }


    // =================================================
    // DELETE
    // =================================================
    public void delete(int id, int userId) { // 🔥 added userId param
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {

            stmt.setInt(1, id);
            stmt.setInt(2, userId); // 🔥 set user_id

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Unable to delete student", e);
        }
    }
}
