package org.com.chatapp.daoImpl;

import org.com.chatapp.dao.MessageDao;
import org.com.chatapp.entities.Message;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class MessageDaoImpl implements MessageDao {
    private EntityManagerFactory entityManagerFactory;

    public MessageDaoImpl(){
        this.entityManagerFactory = Persistence.createEntityManagerFactory("chatUnit");
    }

    @Override
    public void sendMessage(Message message) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction trx =  entityManager.getTransaction();
        try {
            trx.begin();
            entityManager.persist(message);
            trx.commit();
        } catch (Exception e) {
            if (trx != null && trx.isActive()) {
                trx.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Message getMessageById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Message message = null;
        try {
            message = entityManager.find(Message.class, id);
        } finally {
            entityManager.close();
        }
        return message;
    }

    @Override
    public List<Message> getAllMessageByUserId(Long userId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Message> messages = null;
        try {
            messages = entityManager.createQuery("SELECT m FROM Message m WHERE m.sender.id = :userId OR m.receiver.id = :userId", Message.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } finally {
            entityManager.close();
        }
        return messages;
    }

    @Override
    public List<Message> getConversation(Long userId1, Long userId2) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Message> messages = null;
        try {
            messages = entityManager.createQuery("SELECT m FROM Message m WHERE (m.sender.id = :userId1 AND m.receiver.id = :userId2) OR (m.sender.id = :userId2 AND m.receiver.id = :userId1)", Message.class)
                    .setParameter("userId1", userId1)
                    .setParameter("userId2", userId2)
                    .getResultList();
        } finally {
            entityManager.close();
        }
        return messages;
    }

    @Override
    public boolean deleteMessageById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction trx = null;
        try {
            trx = entityManager.getTransaction();
            trx.begin();
            Message message = entityManager.find(Message.class, id);
            if (message != null) {
                entityManager.remove(message);
            }
            trx.commit();
        } catch (Exception e) {
            if (trx != null && trx.isActive()) {
                trx.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return true; // Return true if the deletion was successful
    }

    @Override
    public void updateMessage(Long id, String newContent) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction trx = null;
        try {
            trx = entityManager.getTransaction();
            trx.begin();
            Message message = entityManager.find(Message.class, id);
            if (message != null) {
                message.setContent(newContent);
                entityManager.merge(message);
            }
            trx.commit();
        } catch (Exception e) {
            if (trx != null && trx.isActive()) {
                trx.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Message> getRecentMessages(int limit) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Message> messages = null;
        try {
            messages = entityManager.createQuery("SELECT m FROM Message m ORDER BY m.timestamp DESC", Message.class)
                    .setMaxResults(limit)
                    .getResultList();
        } finally {
            entityManager.close();
        }
        return messages;
    }
}
