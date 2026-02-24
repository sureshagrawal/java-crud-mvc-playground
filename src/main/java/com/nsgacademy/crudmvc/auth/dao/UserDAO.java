package com.nsgacademy.crudmvc.auth.dao;

import com.nsgacademy.crudmvc.auth.model.User;
import com.nsgacademy.crudmvc.utils.JDBCUtils;
import com.nsgacademy.crudmvc.exception.DAOException;

import java.sql.*;

public class UserDAO {

    public void register(User user) throws DAOException {

        String sql = "INSERT INTO users(name,email,password) VALUES(?,?,?)";

        try(Connection con = JDBCUtils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());

            ps.executeUpdate();

        } catch(Exception e){
            throw new DAOException("Register failed", e);
        }
    }

    public User login(String email, String password) throws DAOException {

        String sql = "SELECT * FROM users WHERE email=? AND password=?";

        try(Connection con = JDBCUtils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                return u;
            }

        } catch(Exception e){
            throw new DAOException("Login failed", e);
        }

        return null;
    }
}
