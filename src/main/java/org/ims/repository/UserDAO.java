package org.ims.repository;

import org.ims.db.DataBaseConnection;
import org.ims.exception.UserNotFoundException;
import org.ims.model.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDAO {


    /**
     * Method to create a new user in the database
     * @param user object with the attributes validates
     * @return boolean condition to indicate whether the user is already in the database
     */
    public boolean createUser(User user)
    throws SQLException {

        try (Connection conn = DataBaseConnection.getConnection())
        {
            String query = "INSERT INTO users (username, password, email, role, name, lastname, dni) VALUES (?,?,?,?,?,?,?)";
            try (PreparedStatement prsm = conn.prepareStatement(query))
            {
                prsm.setString(1, user.getUsername());
                prsm.setString(2, user.getPassword());
                prsm.setString(3, user.getEmail());
                prsm.setString(4, user.getRole());
                prsm.setString(5, user.getName());
                prsm.setString(6, user.getLastname());
                prsm.setString(7, user.getDni());

                int rowsInserted = prsm.executeUpdate();

                return rowsInserted > 0;
            }
        }
    }

    /**
     * method to search a user login for a given username
     * @return the user object
     */
    public User getUserByUsername(String username)
    throws SQLException {
        User user = null;

        try (Connection conn = DataBaseConnection.getConnection())
        {
            String query = "SELECT * FROM users WHERE username = ?";
            try (PreparedStatement prsm = conn.prepareStatement(query))
            {
                prsm.setString(1, username);
                try (ResultSet rs = prsm.executeQuery())
                {
                    if (rs.next())
                    {
                        user = new User();
                        user.setId(rs.getLong("id"));
                        user.setUsername(rs.getString("name"));
                        user.setPassword(rs.getString("password"));
                        user.setEmail(rs.getString("email"));
                        user.setRole(rs.getString("role"));
                        user.setName(rs.getString("name"));
                        user.setLastname(rs.getString("lastname"));
                        user.setDni(rs.getString("dni"));
                    }
                }
            }
            return user;
        }
    }

    /**
     * This method returns the names of the columns from the users table.
     * @return Lista of column names
     */
    public String[] getColumnNames()
    throws SQLException {

        try(Connection conn = DataBaseConnection.getConnection())
        {
            String query = "SELECT * FROM users LIMIT 1";
            try(PreparedStatement prsm = conn.prepareStatement(query))
            {
                try(ResultSet rs = prsm.executeQuery())
                {
                    String[] columnNames = new String[rs.getMetaData().getColumnCount()];
                    for(int i = 0; i < rs.getMetaData().getColumnCount(); i++)
                    {
                        columnNames[i] = rs.getMetaData().getColumnName(i+1);
                    }
                    return columnNames;
                }
            }
        }
    }

    /**
     * This method queries the database to return all registered users.
     * @return all registered users
     */
    public ArrayList<User> getAllUsers()
    throws SQLException  {
        ArrayList<User> users = new ArrayList<>();

        try (Connection conn = DataBaseConnection.getConnection()) {
            String query = "SELECT * FROM users";
            try (PreparedStatement prsm = conn.prepareStatement(query))
            {
                try(ResultSet rs = prsm.executeQuery()){
                    while(rs.next()){
                        User user = new User();
                        user.setId(rs.getLong("id"));
                        user.setUsername(rs.getString("username"));
                        user.setPassword(rs.getString("password"));
                        user.setEmail(rs.getString("email"));
                        user.setRole(rs.getString("role"));
                        user.setName(rs.getString("name"));
                        user.setLastname(rs.getString("lastname"));
                        user.setDni(rs.getString("dni"));
                        users.add(user);
                    }
                }
            }
        }
        return users;
    }

    /**
     * This method queries the database to delete an existing user selected in the table through the id.
     * @param user the user to be deleted selected in the table
     */
    public boolean deleteUser(User user)
    throws SQLException  {

        try (Connection conn = DataBaseConnection.getConnection()) {
            String query = "DELETE FROM users WHERE id = ?";
            try (PreparedStatement prsm = conn.prepareStatement(query))
            {
                prsm.setLong(1, user.getId());
                prsm.executeUpdate();
                return true;
            }
        }
    }

    /**
     * This method queries the database to update an existing user's details.
     *
     * @param id the id of the user to update
     * @param user the updated user object
     */
    public boolean updateUser(Long id, User user)
    throws SQLException,
    UserNotFoundException {

        try (Connection conn = DataBaseConnection.getConnection()) {
            String query = "UPDATE users SET username = ?, password =?, email =?, role =?, name =?, lastname =?, dni =? WHERE id = ?";
            try (PreparedStatement prsm = conn.prepareStatement(query))
            {
                prsm.setString(1, user.getUsername());
                prsm.setString(2, user.getPassword());
                prsm.setString(3, user.getEmail());
                prsm.setString(4, user.getRole());
                prsm.setString(5, user.getName());
                prsm.setString(6, user.getLastname());
                prsm.setString(7, user.getDni());
                prsm.setLong(8,id);

                if (prsm.executeUpdate() == 0 )
                    throw new UserNotFoundException("User not found in database");
                return true;
            }
        }
    }

}
