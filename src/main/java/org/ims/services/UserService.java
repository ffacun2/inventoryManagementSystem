package org.ims.services;

import org.ims.exception.InvalidImputDataException;
import org.ims.exception.UserNotFoundException;
import org.ims.model.User;
import org.ims.repository.UserDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserService {

    private final UserDAO userDAO;


    public UserService (UserDAO userDAO){
        this.userDAO = userDAO;
    }

    /**
     * This method validate the introduced values for creating a new user.
     * @param username the username must be unique and greater than six length and alphanumeric.
     * @param password the password must be greater than 6  and less tan 18 length, alphanumeric and at least one special characters.
     * @param email the email must be unique and valid.
     * @param role the role is the permissions
     * @param name name of the person
     * @param lastName last name of the person
     * @param DNI   the DNI of the person
     */
    public User validateUser(String username, String password, String email, String role, String name, String lastName, String DNI) {
        if (username != null && username.length() < 6 && !username.matches("[a-zA-Z0-9]"))
            throw new InvalidImputDataException("Incorrect user entered.\n#length minimun 6 \n#alphanumeric\n#no special characters");
        if (password != null && password.length() < 6 && password.length() > 18 && !password.matches("[0]"))
            throw  new InvalidImputDataException("Incorrect password entered.\n#length minimun 6 and maximun 18\n#alphanumeric ");
        if (email != null && email.matches(""))
            throw new InvalidImputDataException("");
        if(name != null && name.matches(""))
            throw new InvalidImputDataException("");
        if(lastName!= null && lastName.matches(""))
            throw new InvalidImputDataException("");
        if(DNI!= null &&!DNI.matches("\\d{9}"))
            throw new InvalidImputDataException("Incorrect DNI entered.\n#9 digits ");

        return new User(username,password,email,role,name,lastName,DNI);
    }

    /**
     * This method returns the names of the columns from the user's table
     * @return lista of String with column names
     */
    public String[] getColumnNames()
    throws SQLException {
        return userDAO.getColumnNames();
    }

    /**
     * Calls the method that return a list of all users from the user table for showing
     * in the table view.
     */
    public ArrayList<User> getAllUsers()
    throws SQLException {
        return userDAO.getAllUsers();
    }

    /**
     * Calls the method to create a new previously verified user
     * @param user with parameters validated
     */
    public boolean createUser(User user)
    throws SQLException {
        return userDAO.createUser(user);
    }

    /**
     * Calls the method to update an existing user in the database
     *
     * @param id
     * @param user with parameters validated
     */
    public boolean updateUser(Long id, User user)
    throws SQLException,
    UserNotFoundException {
        return userDAO.updateUser(id,user);
    }

    /**
     * Calls the method to delete an existing user from the database
     * @param user with parameters validated
     */
    public boolean deleteUser(User user)
    throws SQLException {
        return userDAO.deleteUser(user);
    }

}
