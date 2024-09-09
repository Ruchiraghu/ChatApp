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
//        System.out.println("Message sent to "+message.getReceiverId());

    }
    public List<Message> getAllMessageByRecipientId(Long recipientId){
        return messageDao.getAllMessageByRecipientId(recipientId);
    }

    public List<Message> getConversation(Long userId1,Long userId2){
        return messageDao.getConversation(userId1,userId2);
    }
    public void deleteMessage(Long id){
        boolean deleted = messageDao.deleteMessageById(id);
        if (deleted){
            System.out.println("Message deleted successfully!");
        } else{
            System.out.println("Message deletion failed.");
        }
    }
    public void updateMessage(Long id,String newContent){
        Message message = messageDao.getMessageById(id);
        if(message!=null){
            message.setContent(newContent);
            messageDao.updateMessage(id,newContent);
        }
    }
    public List<Message> getRecentMessages(int limit){

        return messageDao.getRecentMessages(limit);
    };
    public List<Message> getMessagesByGroup(Long groupId) {
        return messageDao.getMessagesByGroup(groupId);
    }
}
