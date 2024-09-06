package org.com.chatapp.daoImpl;

import org.com.chatapp.dao.UserDao;
import org.com.chatapp.entities.User;
import org.com.chatapp.exception_handling.UserNotFound;

import javax.persistence.*;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private EntityManagerFactory entityManagerFactory;
    EntityManager entityManager = null;
    EntityTransaction trx = null;
    public UserDaoImpl()
    {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("chatUnit");
    }
   @Override
    public void saveUser(User user)throws UserNotFound {
        try{
            entityManager = entityManagerFactory.createEntityManager();
            trx = entityManager.getTransaction();
            trx.begin();
            entityManager.persist(user);
            trx.commit();
        }catch (Exception e){
            if (trx!=null&& trx.isActive()){
                trx.rollback();
            }
            throw new UserNotFound("User not found"+e.getMessage());
        }   finally {
            if (entityManager!=null){
                entityManager.close();
            }
        }
   }

    @Override
    public User getUserById(Long id) throws UserNotFound{
        User user = null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            user = entityManager.find(User.class,id);
        }finally {
            if (entityManager!=null){
                entityManager.close();
            }
        }
        return user;
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFound {
        User user = null;
        try {
            // Initialize the EntityManager
            entityManager = entityManagerFactory.createEntityManager();

            // Execute the query
            user = entityManager.createQuery("FROM User WHERE username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;  // Return null if no user is found
        } catch (Exception e) {
            throw new UserNotFound("Error while retrieving user by username: " + e.getMessage());
        } finally {
            if (entityManager != null) {
                entityManager.close();  // Close the EntityManager
            }
        }
        return user;
    }


    @Override
    public void updateUser(User user) throws UserNotFound{
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
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public void deleteUser(Long id)throws UserNotFound {
        try{
            entityManager = entityManagerFactory.createEntityManager();
            trx = entityManager.getTransaction();
            trx.begin();
            User user = entityManager.find(User.class,id);
            if (user!=null){
                entityManager.remove(user);
            }
        trx.commit();
        }catch (Exception e){
            if (trx!=null&&trx.isActive()){
                trx.rollback();
            }
            throw new UserNotFound("User not found"+e.getMessage());
        }  finally {
            if (entityManager!=null){
                entityManager.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers()throws UserNotFound {
        List<User> users = null;
        try{
            entityManager  = entityManagerFactory.createEntityManager();
            users = entityManager.createQuery("FROM User",User.class)
                    .getResultList();
        }finally {
            if (entityManager!=null){
                entityManager.close();

            }        }
        return users;
    }

    @Override
    public boolean userExists(String username)throws UserNotFound {
        return getUserByUsername(username)!=null;
    }

    @Override
    public User authenticateUser(String username, String password)throws UserNotFound {
        User users = null;
        try{
            entityManager = entityManagerFactory.createEntityManager();
            users = entityManager.createQuery("FROM User WHERE username = :username AND password = :password",User.class)
                    .setParameter("username",username)
                    .setParameter("password",password)
                    .getSingleResult();
        }catch (Exception e){
            throw new UserNotFound("User not found"+e.getMessage());
        }
        finally {
            if (entityManager!=null){
                entityManager.close();
            }
        }
        return users;
    }

}
