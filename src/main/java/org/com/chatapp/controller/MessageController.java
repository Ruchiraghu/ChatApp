package org.com.chatapp.controller;
import org.com.chatapp.entities.User;
import org.com.chatapp.exception_handling.UserNotFound;
import org.com.chatapp.service.MessageService;
import org.com.chatapp.entities.Message;
import org.com.chatapp.service.UserService;
import org.com.chatapp.utility.ScannerUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class MessageController {
    private final MessageService messageService;
    private final UserService userService;
    private final Scanner sc;
public MessageController(MessageService messageService,UserService userService){
    this.messageService = messageService;
    this.userService = userService;
    this.sc = new Scanner(System.in);
}

public void handleMessageManagement() throws UserNotFound {
    boolean running = true;
    while (running){
        System.out.println("Choose an action:\n" +
                "1.Send a message\n" +
                "2.Get messages by recipient\n"+
                "3.Delete a message\n"+
                "4.Get message content\n"+
                "5.List all messages\n"+
                "6.Exit");
        int choice =sc.nextInt();
        sc.nextLine();
        switch (choice){
            case 1->sendMessage();
            case 2->getMessagesByRecipient();
            case 3->deleteMessage();
            case 4->getMessagesByContent();
            case 5->listAllMessages();
            case 6->{running =false;
                System.out.println("Exiting...");
            }
            default->System.out.println("Invalid choice. Please try again.");
        }
    }
}
    private void sendMessage() throws UserNotFound {
    System.out.println("Enter sender ID: ");
    Long senderId = sc.nextLong();
    System.out.println("Enter receiver ID:");
    Long receiverId = sc.nextLong();
    sc.nextLine();
    System.out.println("Enter message: ");
    String content = sc.nextLine();
    Message message = new Message();
    User sender = userService.getUserById(senderId);
    User receiver = userService.getUserById(receiverId);
    message.setSender(sender);
    message.setReceiver(receiver);
    message.setContent(content);
    message.setTimestamp(LocalDateTime.now());
    System.out.println("Message: " + message.toString());
    messageService.sendMessage(message);
    System.out.println("Message sent successfully!");
}
    private void listAllMessages() {
        System.out.println("Enter userId to list messages: ");
        Long userId = sc.nextLong();
        sc.nextLine();
        List<Message> messages = messageService.getAllMessageByUserId(userId);
        messages.forEach(System.out::println);
    }

    private void deleteMessage(){
        System.out.println("Enter message ID to delete: ");
        Long id = sc.nextLong();
        sc.nextLine();
        messageService.deleteMessage(id);
    }

    private void getMessagesByContent() {
        System.out.println("Enter recipient ID: ");
        Long recipientId = sc.nextLong();
        sc.nextLine();
        List<Message> messages = messageService.getAllMessageByUserId(recipientId);
        messages.forEach(System.out::println);
    }

    private void getMessagesByRecipient() {
        System.out.println("Enter recipient ID: ");
        Long recipientId = sc.nextLong();
        sc.nextLine();
        List<Message> messages = messageService.getAllMessageByUserId(recipientId);
        messages.forEach(System.out::println);
    }

    public void chatBetweenUsers() throws UserNotFound {
        System.out.println("Enter user ID 1:");
        Long userId1 = ScannerUtil.getLong("User ID 1:");
        System.out.println("Enter user ID 2:");
        Long userId2 = ScannerUtil.getLong("User ID 2:");

        User user1 = userService.getUserById(userId1);
        User user2 = userService.getUserById(userId2);

        if (user1 == null || user2 == null) {
            throw new UserNotFound("One or both users not found.");
        }

        System.out.println("Chat started between " + user1.getName() + " and " + user2.getName() + ".");
        System.out.println("Type 'exit' to end the chat.");

        boolean chatting = true;
        while (chatting) {
            // User 1 sends a message
            System.out.print(user1.getName() + ": ");
            String message1 = sc.nextLine();
            if (message1.equalsIgnoreCase("exit")) {
                chatting = false;
                break;
            }

            Message msg1 = new Message();
            msg1.setSenderId(userId1);
            msg1.setReceiverId(userId2);
            msg1.setContent(message1);
            msg1.setTimestamp(LocalDateTime.now());
            messageService.sendMessage(msg1);

            // User 2 sends a message
            System.out.print(user2.getName() + ": ");
            String message2 = sc.nextLine();
            if (message2.equalsIgnoreCase("exit")) {
                chatting = false;
                break;
            }

            Message msg2 = new Message();
            msg2.setSenderId(userId2);
            msg2.setReceiverId(userId1);
            msg2.setContent(message2);
            msg2.setTimestamp(LocalDateTime.now());
            messageService.sendMessage(msg2);
        }

        System.out.println("Chat ended.");
    }


}