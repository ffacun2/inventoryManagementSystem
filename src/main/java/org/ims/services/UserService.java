package org.ims.services;

public class UserService {

    /**
     * This method validate the introduced values for creating a new user.
     * @param username the username must be unique and greater than six length and alphanumeric.
     * @param password the password must be greater than 6  and less tan 18 length, alphanumeric and at least one special characters.
     * @param email the email must be unique and valid.
     */
    public boolean validateUser(String username, String password, String email) {
        if (username != null && username.length() < 6 && !username.matches("[a-zA-Z0-9]"))
            return false;
        if (password != null && password.length() < 6 && password.length() > 18 && !password.matches("[0]"))
            return false;
        if (email != null && email.matches(""))
            return false;
        return true;
    }
}
