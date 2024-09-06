package org.com.chatapp.controller;

import org.com.chatapp.entities.User;
import org.com.chatapp.exception_handling.UserNotFound;
import org.com.chatapp.service.UserService;

import java.util.Scanner;

public class UserController {
    private final UserService userService;
    private final Scanner sc;

    public UserController(UserService userService) {
        this.userService = userService;
        this.sc = new Scanner(System.in);
    }

    public void handleUserManagement() throws UserNotFound {
        boolean running = true;
        while (running) {
            System.out.println("\nChoose an action:\n" +
                    "1. Register a new user\n" +
                    "2. Get user by ID\n" +
                    "3. Get user by username\n" +
                    "4. List all users\n" +
                    "5. Update user\n" +
                    "6. Delete user\n" +
                    "7. Back to the main menu");
            int choice = sc.nextInt();
            sc.nextLine();  // Consume newline

            switch (choice) {
                case 1 -> registerUser();
                case 2 -> getUserById();
                case 3 -> getUserByUsername();
                case 4 -> listAllUsers();
                case 5 -> updateUser();
                case 6 -> deleteUser();
                case 7 ->running = false;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void registerUser() throws UserNotFound
    {
        System.out.println("Enter name:");
        String name = sc.nextLine();
        System.out.println("Enter username:");
        String username = sc.nextLine();
        System.out.println("Enter password:");
        String password = sc.nextLine();


        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        userService.saveUser(user);
        System.out.println("User registered successfully.");
    }

    private void getUserById() throws UserNotFound {
        System.out.println("Enter user ID:");
        Long id = sc.nextLong();
        sc.nextLine();  // Consume newline
        User user = userService.getUserById(id);
        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("User not found.");
        }
    }

    private void getUserByUsername() throws UserNotFound {
        System.out.println("Enter username:");
        String username = sc.nextLine();
        User user = userService.getUserByUsername(username);
        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("User not found.");
        }
    }

    private void listAllUsers() throws UserNotFound {
        userService.getAllUsers().forEach(System.out::println);
    }

    public void updateUser() throws UserNotFound {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter user ID:");
        Long userId = scanner.nextLong();
        scanner.nextLine();  // Consume the newline character

        User user = userService.getUserById(userId);
        if (user == null) {
            System.out.println("User not found!");
            return;
        }

        System.out.println("What would you like to update?");
        System.out.println("1. Username");
        System.out.println("2. Password");
        System.out.println("3. Name");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        switch (choice) {
            case 1:
                System.out.println("Enter new username:");
                String newUsername = scanner.nextLine();
                user.setUsername(newUsername);
                break;
            case 2:
                System.out.println("Enter new password:");
                String newPassword = scanner.nextLine();
                user.setPassword(newPassword);
                break;
            case 3:
                System.out.println("Enter new name:");
                String newName = scanner.nextLine();
                user.setName(newName);
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        userService.updateUser(user);
        System.out.println("User updated successfully.");
    }


    private void deleteUser() throws UserNotFound {
        System.out.println("Enter user ID:");
        Long id = sc.nextLong();
        sc.nextLine();  // Consume newline
        userService.deleteUser(id);
        System.out.println("User deleted successfully.");
    }
}
