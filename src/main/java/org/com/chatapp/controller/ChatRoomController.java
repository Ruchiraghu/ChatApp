package org.com.chatapp.controller;

import org.com.chatapp.entities.ChatRoom;
import org.com.chatapp.service.ChatRoomService;

import java.util.Scanner;
import java.util.List;

public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final Scanner sc;

    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
        this.sc = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\nChoose an action:\n" +
                    "1. Create a new chat room\n" +
                    "2. Get chat room by ID\n" +
                    "3. Get chat room by name\n" +
                    "4. List all chat rooms\n" +
                    "5. Update chat room name\n" +
                    "6. Delete a chat room\n" +
                    "7. Exit");
            int choice = sc.nextInt();
            sc.nextLine();  // Consume newline

            switch (choice) {
                case 1 -> createChatRoom();
                case 2 -> getChatRoomById();
                case 3 -> getChatRoomByName();
                case 4 -> listAllChatRooms();
                case 5 -> updateChatRoomName();
                case 6 -> deleteChatRoom();
                case 7 -> {
                    running = false;
                    System.out.println("Exiting...");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createChatRoom() {
        System.out.println("Enter chat room name:");
        String name = sc.nextLine();
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(name);
        chatRoomService.saveChatRoom(chatRoom);
        System.out.println("Chat room created successfully.");
    }

    private void getChatRoomById() {
        System.out.println("Enter chat room ID:");
        Long id = sc.nextLong();
        sc.nextLine();  // Consume newline
        ChatRoom chatRoom = chatRoomService.getChatRoomById(id);
        if (chatRoom != null) {
            System.out.println(chatRoom);
        } else {
            System.out.println("Chat room not found.");
        }
    }

    private void getChatRoomByName() {
        System.out.println("Enter chat room name:");
        String name = sc.nextLine();
        ChatRoom chatRoom = chatRoomService.getChatRoomByName(name);
        if (chatRoom != null) {
            System.out.println(chatRoom);
        } else {
            System.out.println("Chat room not found.");
        }
    }

    private void listAllChatRooms() {
        List<ChatRoom> chatRooms = chatRoomService.getAllChatRooms();
        if (chatRooms.isEmpty()) {
            System.out.println("No chat rooms available.");
        } else {
            chatRooms.forEach(System.out::println);
        }
    }

    private void updateChatRoomName() {
        System.out.println("Enter chat room ID:");
        Long id = sc.nextLong();
        sc.nextLine();  // Consume newline
        System.out.println("Enter new chat room name:");
        String newName = sc.nextLine();
        chatRoomService.updateChatRoomName(id, newName);
        System.out.println("Chat room name updated successfully.");
    }

    private void deleteChatRoom() {
        System.out.println("Enter chat room ID:");
        Long id = sc.nextLong();
        sc.nextLine();  // Consume newline
        chatRoomService.deleteChatRoom(id);
        System.out.println("Chat room deleted successfully.");
    }
}
