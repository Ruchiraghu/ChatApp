package org.com.chatapp.dao;

import org.com.chatapp.entities.ChatRoom;

import java.util.List;

public interface ChatRoomDao {
void saveChatRoom(ChatRoom chatRoom);
ChatRoom getChatRoomById(Long id);
ChatRoom getChatRoomByName(String name);
List<ChatRoom>getAllChatRooms();
void deleteChatRoom(Long id);
void updateChatRoom(ChatRoom chatRoom);
}
