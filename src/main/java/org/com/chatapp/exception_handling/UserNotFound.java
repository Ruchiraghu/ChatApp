package org.com.chatapp.exception_handling;

public class UserNotFound extends Exception{
    public UserNotFound() {

    }

    public UserNotFound(String message) {
        super(message);
    }
}
