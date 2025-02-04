package org.ims.services;


import org.ims.model.User;
import org.ims.repository.UserDAO;

import java.sql.SQLException;
import java.util.prefs.Preferences;

public class LoginService {

    private static final String REMEMBER_ME_KEY = "rememberMe";
    private static final String USER_SESSION_KEY = "userSession";

    private final UserDAO userDAO;

    public LoginService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User authenticateUser(String username, String password)
    throws SQLException {
        if ( username != null && password != null) {
            User userLogin = userDAO.getUserByUsername(username);
            if (userLogin != null && userLogin.getUsername().equals(username) && userLogin.getPassword().equals(password))
                return userLogin;
        }
        return null;
    }

    public void saveCredentials(String username, boolean rememberMe) {
        Preferences pref = Preferences.userNodeForPackage(LoginService.class);
        if (rememberMe) {
            pref.putBoolean(REMEMBER_ME_KEY, true);
            pref.put(USER_SESSION_KEY, username);
        }
        else {
            pref.remove(REMEMBER_ME_KEY);
            pref.remove(USER_SESSION_KEY);
        }
    }

    public String loadCredentials() {
        Preferences pref = Preferences.userNodeForPackage(LoginService.class);
        if (pref.getBoolean(REMEMBER_ME_KEY, false)) {
            return pref.get(USER_SESSION_KEY, "");
        }
        return "";
    }

}
