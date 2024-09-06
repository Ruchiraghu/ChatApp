package org.com.chatapp.service;

import org.com.chatapp.dao.ChatRoomDao;
import org.com.chatapp.daoImpl.ChatRoomDaoImpl;
import org.com.chatapp.entities.ChatRoom;

import java.util.List;

public class ChatRoomService {
    private ChatRoomDao chatRoomDao;
    public ChatRoomService(ChatRoomDao chatRoomDao){
        this.chatRoomDao = chatRoomDao;
    }

   public void saveChatRoom(ChatRoom chatRoom){
       chatRoomDao.saveChatRoom(chatRoom);
   }
   public ChatRoom getChatRoomById(Long id){
       return chatRoomDao.getChatRoomById(id);

   }
   public ChatRoom getChatRoomByName(String name){
        ChatRoom chatRoom = chatRoomDao.getChatRoomByName(name);
        return chatRoom;
   }
   public List<ChatRoom> getAllChatRooms(){
       return chatRoomDao.getAllChatRooms();
   }
   public void deleteChatRoom(Long id){
        chatRoomDao.deleteChatRoom(id);
   }
   public void updateChatRoomName(Long id, String newName){
        ChatRoom chatRoom = chatRoomDao.getChatRoomById(id);
        if (chatRoom!=null){
            chatRoom.setName(newName);
            chatRoomDao.updateChatRoom(chatRoom);
        }
        else {
            System.out.println("ChatRoom with ID " + id + " not found.");
        }
   }

}
