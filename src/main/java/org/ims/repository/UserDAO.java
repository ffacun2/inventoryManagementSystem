package org.ims.repository;

import org.ims.db.DataBaseConnection;
import org.ims.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {


    /**
     * Method to create a new user in the database
     * @return boolean condition to indicate whether the user is already in the database
     */
    public boolean createUser(String username, String password, String email, String category) {

        try (Connection conn = DataBaseConnection.getConnection()) {
            String query = "INSERT INTO users (username, password, email, category) VALUES (?,?,?,?)";
            try (PreparedStatement prsm = conn.prepareStatement(query))
            {
                prsm.setString(1, username);
                prsm.setString(2, password);
                prsm.setString(3, email);
                prsm.setString(4, category);
                try(ResultSet rs = prsm.executeQuery()){

                }
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }

        return true;
    }

    /**
     * method to search a user login for a given username
     * @return the user object
     */
    public User getUserByUsername(String username){
        User user = null;

        try (Connection conn = DataBaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ?";
            try (PreparedStatement prsm = conn.prepareStatement(query))
            {
                prsm.setString(1, username);
                try(ResultSet rs = prsm.executeQuery()){
                    if(rs.next()){
                        user.setUsername(rs.getString(1));
                        user.setPassword(rs.getString(2));
                        user.setEmail(rs.getString(3));
                        user.setCategory(rs.getString(4));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }

        return user;
    }
}
