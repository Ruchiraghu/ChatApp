package org.com.chatapp.service;

import org.com.chatapp.dao.UserDao;
import org.com.chatapp.daoImpl.UserDaoImpl;
import org.com.chatapp.entities.User;
import org.com.chatapp.exception_handling.UserNotFound;

import java.util.List;

public class UserService
{
    private UserDao userDao;

    public UserService(UserDao userDao){
        this.userDao = userDao;
    }

    public void registerUser(String username, String password) throws UserNotFound {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userDao.saveUser(user);
    }
    public void saveUser(User user) throws UserNotFound {
        if (userDao.userExists(user.getUsername())) {
            throw new UserNotFound("User already exists with username: " + user.getUsername());
        }

        // If user doesn't exist, proceed to save the user
        userDao.saveUser(user);
    }

    public User getUserById(Long id) throws UserNotFound{
        User user = userDao.getUserById(id);
        if (user==null){
            throw new UserNotFound("User not found with ID:"+id);

        }
        return user;
    }
    public User getUserByUsername(String username) throws UserNotFound{
        User user = userDao.getUserByUsername(username);
        if (user==null){
            throw new UserNotFound("User not found with username: "+username);
        }
    return user;
    }
    public void updateUser(User user) throws UserNotFound{
        User existingUser = getUserById(user.getId());
        if (existingUser==null){
            throw new UserNotFound("User not found with ID: "+user.getId());

        }        userDao.updateUser(user);
    }
    public void deleteUser(Long id) throws UserNotFound{
        User user = getUserById(id);
        if (user==null){
            throw new UserNotFound("User not found with ID: "+id);
        }
        userDao.deleteUser(id);
    }
    public List<User> getAllUsers() throws UserNotFound{
        return userDao.getAllUsers();
    }
    public boolean userExists(String username) throws UserNotFound{
        return userDao.getUserByUsername(username)!=null;
    }
    public User authenticateUser(String username, String password) throws UserNotFound{
        User user = userDao.getUserByUsername(username);
        if (user==null||!user.getPassword().equals(password)){
            throw new UserNotFound("Invalid username or password");
        }
    return user;
    }
}
