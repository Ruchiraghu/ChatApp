package org.com.chatapp.controller;

import org.com.chatapp.entities.GroupChat;
import org.com.chatapp.entities.Message;
import org.com.chatapp.entities.User;
import org.com.chatapp.exception_handling.UserNotFound;
import org.com.chatapp.service.GroupService;
import org.com.chatapp.service.MessageService;
import org.com.chatapp.service.UserService;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

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
                    "4. Exit");
            int choice = sc.nextInt();
            sc.nextLine();  // Consume newline

            switch (choice) {
                case 1 -> createGroupChat();
                case 2 -> joinGroupChat();
                case 3 -> viewMessagesByGroup();
                case 4 -> {
                    running = false;
                    System.out.println("Exiting...");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewMessagesByGroup() {
        System.out.println("Enter group ID:");
        int gId = sc.nextInt();
        List<Message> groupMessages = messageService.getMessagesByGroup(gId);
        for (Message msg : groupMessages) {
            System.out.println(msg.getTimestamp() + " - " + (msg.getSender() != null ? msg.getSender().getName() : "Unknown") + ": " + msg.getMessage());
        }
    }

    private void joinGroupChat() throws UserNotFound {
        System.out.println("Enter group ID:");
        int grpId = sc.nextInt();
        sc.nextLine();  // Consume newline
        System.out.println("Enter user IDs to add to group (comma separated):");
        String[] userIds = sc.nextLine().split(",");
        GroupChat existingGroup = groupService.getGroup(grpId);
        Set<User> users = existingGroup.getUsers();
        for (String idStr : userIds) {
            int userId = Integer.parseInt(idStr.trim());
            User user = userService.getUserById(Long.valueOf(userId));
            if (user != null) {
                users.add(user);
            }
        }
        existingGroup.setUsers(users);
        groupService.createGroup(existingGroup); // Save updated group
        System.out.println("Users added to group.");
    }

    private void createGroupChat() {
        System.out.println("Enter group name:");
        String groupName = sc.nextLine();
        GroupChat group = new GroupChat();
        group.setName(groupName);
        groupService.createGroup(group);
        System.out.println("Group created successfully.");
    }

}
