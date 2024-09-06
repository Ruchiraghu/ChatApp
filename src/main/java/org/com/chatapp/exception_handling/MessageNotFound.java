package org.com.chatapp.exception_handling;

public class MessageNotFound extends Exception{
    public MessageNotFound() {
        super();
    }

    public MessageNotFound(String message) {
        super(message);
    }
}
