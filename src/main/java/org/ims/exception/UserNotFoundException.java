package org.ims.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String userNotFoundInDatabase) {
        super(userNotFoundInDatabase);
    }

    public UserNotFoundException(String userNotFoundInDatabase, Throwable e) {
        super(userNotFoundInDatabase, e);
    }
}
