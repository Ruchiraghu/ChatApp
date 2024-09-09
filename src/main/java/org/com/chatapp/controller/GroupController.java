package org.com.chatapp.controller;

import org.com.chatapp.entities.GroupChat;
import org.com.chatapp.entities.Message;
import org.com.chatapp.entities.User;
import org.com.chatapp.exception_handling.UserNotFound;
import org.com.chatapp.service.GroupService;
import org.com.chatapp.service.MessageService;
import org.com.chatapp.service.UserService;

import java.util.*;

public class GroupController {
    private final MessageService messageService;
    private final GroupService groupService;
    private final UserService userService;
    private final Scanner sc;

    public GroupController(MessageService messageService, GroupService groupService, UserService userService, Scanner sc) {
        this.messageService = messageService;
        this.groupService = groupService;
        this.userService = userService;
        this.sc = sc;
    }

    public void groupChat() throws UserNotFound {
        boolean running = true;
        while (running) {
            System.out.println("\nChoose an action:\n" +
                    "1. Create group\n" +
                    "2. Add Users to Group\n" +
                    "3. View Messages by Group\n" +
                    "4. Group Chat\n" +
                    "5. Exit");
            int choice = sc.nextInt();
            sc.nextLine();  // Consume newline

            switch (choice) {
                case 1 -> createGroup();
                case 2 -> addUsersToGroup();
                case 3 -> {
                    System.out.println("Enter group ID to view messages:");
                    Long groupId = sc.nextLong();
                    sc.nextLine(); // Consume newline
                    viewMessagesByGroup(groupId);
                }
                case 4 -> doGroupChatting();
                case 5 -> {
                    running = false;
                    System.out.println("Exiting...");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewMessagesByGroup(Long groupId) {
        // Call service to get messages by group
        List<Message> groupMessages = messageService.getMessagesByGroup(groupId);
        if (groupMessages != null && !groupMessages.isEmpty()) {
            for (Message message : groupMessages) {
                // Assuming you have methods to get the sender's name and message content
                String senderName = message.getSender().getName();
                String content = message.getContent();

                System.out.println(senderName + ": " + content);
            }
        } else {
            System.out.println("No messages found for this group.");
        }
    }


    private void createGroup() {
        System.out.println("Enter group name:");
        String groupName = sc.nextLine();
        GroupChat group = new GroupChat();
        group.setName(groupName);
        groupService.createGroup(group);
        System.out.println("Group created successfully.");
    }

    private void doGroupChatting() throws UserNotFound {
        System.out.println("Enter group ID to join the chat:");
        Long groupId = sc.nextLong();
        sc.nextLine();  // Consume newline

        GroupChat group = groupService.getGroup(groupId);
        if (group == null) {
            System.out.println("Group not found.");
            return;
        }

        System.out.println("You have joined the group: " + group.getName());

        boolean chatting = true;
        while (chatting) {
            System.out.println("Choose an action:\n" +
                    "1. Send message\n" +
                    "2. View all messages\n" +
                    "3. Exit chat");
            int choice = sc.nextInt();
            sc.nextLine();  // Consume newline

            switch (choice) {
                case 1 -> sendMessageToGroup(groupId);
                case 2 -> viewMessagesByGroup(groupId);
                case 3 -> {
                    chatting = false;
                    System.out.println("Exiting group chat...");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void sendMessageToGroup(Long groupId) throws UserNotFound {
        System.out.println("Enter your user ID:");
        Long userId = sc.nextLong();
        sc.nextLine();  // Consume newline

        User sender = userService.getUserById(userId);
        if (sender == null) {
            throw new UserNotFound("User not found with ID: " + userId);
        }

        System.out.println("Enter your message:");
        String messageText = sc.nextLine();

        GroupChat group = groupService.getGroup(groupId);
        if (group == null) {
            System.out.println("Group not found.");
            return;
        }

        Message message = new Message();
        message.setSender(sender);
        message.setContent(messageText);
        message.setGroupChat(group);  // Set the groupChat, not groupId

        messageService.sendMessage(message);

        System.out.println("Message sent to the group.");
    }

    public void addUsersToGroup() {
        System.out.print("Enter Group ID: ");
        Long groupId = sc.nextLong();

        System.out.print("Enter number of users to add: ");
        int numberOfUsers = sc.nextInt();
        sc.nextLine(); // Consume newline

        if (numberOfUsers <= 0) {
            System.out.println("Number of users must be greater than zero.");
            return;
        }

        List<Long> userIds = new ArrayList<>();
        for (int i = 0; i < numberOfUsers; i++) {
            System.out.print("Enter User ID: ");
            Long userId = sc.nextLong();
            userIds.add(userId);
        }

        groupService.addUsersToGroup(groupId, userIds);

        System.out.println("Users added to the group successfully.");
    }
}
