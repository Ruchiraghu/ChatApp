package org.com.chatapp.service;

import org.com.chatapp.dao.MessageDao;
import org.com.chatapp.entities.Message;

import java.util.List;

public class MessageService {
    private final MessageDao messageDao;

    public MessageService(MessageDao messageDao){
        this.messageDao = messageDao;
    }

    public void sendMessage(Message message){
        messageDao.sendMessage(message);
    }

    public List<Message> getMessagesByGroup(Long groupId) {
        return messageDao.getMessagesByGroup(groupId);
    }
}
