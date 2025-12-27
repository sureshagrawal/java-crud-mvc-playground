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
            "INSERT INTO students (name, email, mobile) VALUES (?, ?, ?)";

//    private static final String SELECT_ALL_SQL =
//            "SELECT * FROM students ORDER BY id";

    private static final String BASE_SELECT_SQL =
            "SELECT id, name, email, mobile FROM students";

    private static final String PAGINATION_SQL =
            " LIMIT ? OFFSET ?";

    private static final String COUNT_SQL =
            "SELECT COUNT(*) FROM students";

    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM students WHERE id=?";

    private static final String UPDATE_SQL =
            "UPDATE students SET name=?, email=?, mobile=? WHERE id=?";

    private static final String DELETE_SQL =
            "DELETE FROM students WHERE id=?";

    public void insert(Student student) {
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {

            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getMobile());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Unable to insert student", e);
        }
    }

    public List<Student> listStudents(StudentFilter filter, Pagination pagination) {

        List<Student> studentList = new ArrayList<>();

        String sql = BASE_SELECT_SQL;

        boolean hasSearch = filter != null && filter.hasSearch();

        if (hasSearch) {
            sql += " WHERE name ILIKE ? OR email ILIKE ? OR mobile ILIKE ?";
        }

        sql += PAGINATION_SQL;

        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int index = 1;

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

    public int countStudents(StudentFilter filter) {

        String sql = COUNT_SQL;

        boolean hasSearch = filter != null && filter.hasSearch();

        if (hasSearch) {
            sql += " WHERE name ILIKE ? OR email ILIKE ? OR mobile ILIKE ?";
        }

        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (hasSearch) {
                String keyword = "%" + filter.getSearch().trim() + "%";
                ps.setString(1, keyword);
                ps.setString(2, keyword);
                ps.setString(3, keyword);
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

    public Student getStudentById(int id) {
        Student student = null;

        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setInt(1, id);

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

    public void update(Student student) {
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getMobile());
            stmt.setInt(4, student.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Unable to update student", e);
        }
    }

    public void delete(int id) {
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Unable to delete student", e);
        }
    }
}
