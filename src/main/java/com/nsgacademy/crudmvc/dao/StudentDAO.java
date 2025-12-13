package com.nsgacademy.crudmvc.dao;

import com.nsgacademy.crudmvc.exception.DAOException;
import com.nsgacademy.crudmvc.model.Student;
import com.nsgacademy.crudmvc.utils.JDBCUtils;

import java.sql.*;
import java.util.*;

public class StudentDAO {

    private static final String INSERT_SQL =
            "INSERT INTO students (name, email, mobile) VALUES (?, ?, ?)";

    private static final String SELECT_ALL_SQL =
            "SELECT * FROM students ORDER BY id";

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

    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();

        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("mobile")));
            }

        } catch (SQLException e) {
            throw new DAOException("Unable to fetch students", e);
        }

        return list;
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
