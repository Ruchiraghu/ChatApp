package org.com.chatapp.controller;

import org.com.chatapp.entities.User;
import org.com.chatapp.exception_handling.UserNotFound;
import org.com.chatapp.service.MessageService;
import org.com.chatapp.entities.Message;
import org.com.chatapp.service.UserService;
import org.com.chatapp.utility.ScannerUtil;
import java.time.LocalDateTime;
import java.util.Scanner;

public class MessageController {
    private final MessageService messageService;
    private final UserService userService;
    private final Scanner sc;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
        this.sc = new Scanner(System.in);
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

        while (true) {
            // User 1 sends a message
            System.out.print(user1.getName() + ": ");
            String message1 = sc.nextLine();
            if (message1.equalsIgnoreCase("exit")) {
                break;
            }

            Message msg1 = new Message();
            msg1.setSender(user1);
            msg1.setReceiver(user2);
            msg1.setContent(message1);
            msg1.setTimestamp(LocalDateTime.now());
            messageService.sendMessage(msg1);

            // User 2 sends a message
            System.out.print(user2.getName() + ": ");
            String message2 = sc.nextLine();
            if (message2.equalsIgnoreCase("exit")) {
                break;
            }

            Message msg2 = new Message();
            msg2.setSender(user2);
            msg2.setReceiver(user1);
            msg2.setContent(message2);
            msg2.setTimestamp(LocalDateTime.now());
            messageService.sendMessage(msg2);
        }

        System.out.println("Chat ended.");
    }
}
