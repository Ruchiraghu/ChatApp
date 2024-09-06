package org.com.chatapp.main;

import org.com.chatapp.controller.ChatRoomController;
import org.com.chatapp.controller.MessageController;
import org.com.chatapp.controller.UserController;
import org.com.chatapp.dao.ChatRoomDao;
import org.com.chatapp.dao.MessageDao;
import org.com.chatapp.dao.UserDao;
import org.com.chatapp.daoImpl.ChatRoomDaoImpl;
import org.com.chatapp.daoImpl.MessageDaoImpl;
import org.com.chatapp.daoImpl.UserDaoImpl;
import org.com.chatapp.exception_handling.UserNotFound;
import org.com.chatapp.service.UserService;
import org.com.chatapp.service.MessageService;
import org.com.chatapp.service.ChatRoomService;
import org.com.chatapp.utility.ScannerUtil;

public class App {

    private static final UserDao userDao = new UserDaoImpl();
    private static final MessageDao messageDao = new MessageDaoImpl();
    private static final ChatRoomDao chatRoomDao = new ChatRoomDaoImpl();
    private static final UserService userService = new UserService(userDao);
    private static final MessageService messageService = new MessageService(messageDao);
    private static final ChatRoomService chatRoomService = new ChatRoomService(chatRoomDao);

    private static final UserController userController = new UserController(userService);
    private static final MessageController messageController = new MessageController(messageService,userService);
    private static final ChatRoomController chatRoomController = new ChatRoomController(chatRoomService);
    public static void main(String[] args) throws UserNotFound {
        while (true) {
            System.out.println("Welcome to the Chat Application!");
            System.out.println("1. User Management");
            System.out.println("2. Message Management");
            System.out.println("3. Chat between two Users");
            System.out.println("4. Exit");

            int choice = ScannerUtil.getInt("Choose an option:");

            switch (choice) {
                case 1->userController.handleUserManagement();
                case 2->messageController.handleMessageManagement();
                case 3->messageController.chatBetweenUsers();
                case 4-> chatRoomController.start();
                case 5->{System.out.println("Exiting the application.");
                    return;
                }
                default->System.out.println("Invalid option. Please try again.");
            }
        }
    }

}
