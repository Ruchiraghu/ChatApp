package org.com.chatapp.exception_handling;

public class StatusNotFound extends Exception{
    public StatusNotFound() {
        super();
    }

    public StatusNotFound(String message) {
        super(message);
    }
}
