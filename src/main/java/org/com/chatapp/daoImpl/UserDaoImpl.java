package org.com.chatapp.daoImpl;

import org.com.chatapp.dao.UserDao;
import org.com.chatapp.entities.GroupChat;
import org.com.chatapp.entities.User;
import org.com.chatapp.exception_handling.UserNotFound;

import javax.persistence.*;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager = null;
    private EntityTransaction trx = null;

    public UserDaoImpl() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("chatUnit");
    }

    @Override
    public void saveUser(User user) throws UserNotFound {
        try {
            entityManager = entityManagerFactory.createEntityManager();
            trx = entityManager.getTransaction();
            trx.begin();
            entityManager.persist(user);
            trx.commit();
        } catch (Exception e) {
            if (trx != null && trx.isActive()) {
                trx.rollback();
            }
            throw new UserNotFound("Error while saving user: " + e.getMessage());
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public User getUserById(Long id) throws UserNotFound {
        User user = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            user = entityManager.find(User.class, id);
            if (user == null) {
                throw new UserNotFound("User with ID " + id + " not found");
            }
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return user;
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFound {
        User user = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            user = entityManager.createQuery("FROM User WHERE username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;  // Return null if no user is found
        } catch (Exception e) {
            throw new UserNotFound("Error while retrieving user by username: " + e.getMessage());
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return user;
    }

    @Override
    public void updateUser(User user) throws UserNotFound {
        try {
            entityManager = entityManagerFactory.createEntityManager();
            trx = entityManager.getTransaction();
            trx.begin();
            entityManager.merge(user);
            trx.commit();
        } catch (Exception e) {
            if (trx != null && trx.isActive()) {
                trx.rollback();
            }
            throw new UserNotFound("Error while updating user: " + e.getMessage());
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public void deleteUser(Long id) throws UserNotFound {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = entityManagerFactory.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            // First, remove user associations if applicable
            removeUserFromGroups(em, id); // Make sure this method is defined and correct

            // Then, remove the user
            User user = em.find(User.class, id);
            if (user != null) {
                em.remove(user);
            } else {
                throw new UserNotFound("User with ID " + id + " not found.");
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new UserNotFound("Error while deleting user: " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private void removeUserFromGroups(EntityManager em, Long userId) {
        // First, get all groups the user is part of
        List<GroupChat> groups = em.createQuery("SELECT g FROM GroupChat g JOIN g.users u WHERE u.id = :userId", GroupChat.class)
                .setParameter("userId", userId)
                .getResultList();

        // Iterate over each group and remove the user
        for (GroupChat group : groups) {
            group.getUsers().removeIf(user -> user.getId().equals(userId));
            em.merge(group); // Save changes to the group
        }
    }



    @Override
    public List<User> getAllUsers() throws UserNotFound {
        List<User> users = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            users = entityManager.createQuery("FROM User", User.class)
                    .getResultList();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return users;
    }

    @Override
    public boolean userExists(String username) throws UserNotFound {
        return getUserByUsername(username) != null;
    }

    @Override
    public User authenticateUser(String username, String password) throws UserNotFound {
        User user = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            user = entityManager.createQuery("FROM User WHERE username = :username AND password = :password", User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Return null if no matching user is found
        } catch (Exception e) {
            throw new UserNotFound("Error while authenticating user: " + e.getMessage());
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return user;
    }
}
