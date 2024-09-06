package org.com.chatapp.dao;


import org.com.chatapp.entities.Message;

import java.util.List;

public interface MessageDao {
    void sendMessage(Message message);
    Message getMessageById(Long id);
    List<Message> getAllMessageByUserId(Long userId);
    List<Message> getConversation(Long userId1,Long userId2);
    boolean deleteMessageById(Long id);
    void updateMessage(Long id,String newContent);
    List<Message> getRecentMessages(int limit);
}

