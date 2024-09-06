package org.com.chatapp.exception_handling;

public class ChatRoomNotFoundException extends Exception{
    public ChatRoomNotFoundException(){}

    public ChatRoomNotFoundException(String message) {
        super(message);
    }
}

