package org.com.chatapp.dao;

import org.com.chatapp.entities.User;
import org.com.chatapp.exception_handling.UserNotFound;

import java.util.List;

public interface UserDao {
   void saveUser(User user) throws UserNotFound;
   User getUserById(Long id) throws UserNotFound;
   User getUserByUsername(String username) throws UserNotFound;
   void updateUser(User user) throws UserNotFound;
   void deleteUser(Long id) throws UserNotFound;
   List<User> getAllUsers() throws UserNotFound;
   boolean userExists(String username) throws UserNotFound;
   User authenticateUser(String username, String password) throws UserNotFound;
}
