package org.com.chatapp.main;

import org.com.chatapp.controller.GroupController;
import org.com.chatapp.controller.MessageController;
import org.com.chatapp.controller.UserController;
import org.com.chatapp.dao.GroupDao;
import org.com.chatapp.dao.MessageDao;
import org.com.chatapp.dao.UserDao;
import org.com.chatapp.daoImpl.GroupDaoImpl;
import org.com.chatapp.daoImpl.MessageDaoImpl;
import org.com.chatapp.daoImpl.UserDaoImpl;
import org.com.chatapp.exception_handling.UserNotFound;
import org.com.chatapp.service.GroupService;
import org.com.chatapp.service.UserService;
import org.com.chatapp.service.MessageService;
import org.com.chatapp.utility.ScannerUtil;

import java.util.Scanner;

public class App {

    private static final UserDao userDao = new UserDaoImpl();
    private static final MessageDao messageDao = new MessageDaoImpl();
    private static final GroupDao groupDao = new GroupDaoImpl();
    private static final UserService userService = new UserService(userDao);
    public static final MessageService messageService = new MessageService(messageDao);
    private static final GroupService groupService = new GroupService(groupDao);

    private static final UserController userController = new UserController(userService);
    private static final MessageController messageController = new MessageController(messageService,userService);
    private static final Scanner sc = new Scanner(System.in);
    private static final GroupController groupController = new GroupController(messageService,groupService,userService,sc);
    public static void main(String[] args) throws UserNotFound {
        while (true) {
            System.out.println("Welcome to the Chat Application!\n" +
                    "1. User Management\n" +
                    "2. Chat between two Users\n" +
                    "3. Group chat\n" +
                    "4. Exit");

            int choice = ScannerUtil.getInt("Choose an option:");

            switch (choice) {
                case 1->userController.handleUserManagement();
                case 2->messageController.chatBetweenUsers();
                case 3-> groupController.groupChat();
                case 4->{System.out.println("Exiting the application.");
                    return;
                }
                default->System.out.println("Invalid option. Please try again.");
            }
        }
    }

}
