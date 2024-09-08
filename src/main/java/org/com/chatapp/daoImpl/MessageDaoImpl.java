package org.com.chatapp.daoImpl;

import org.com.chatapp.dao.MessageDao;
import org.com.chatapp.entities.Message;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class MessageDaoImpl implements MessageDao {
    private final EntityManagerFactory entityManagerFactory;

    public MessageDaoImpl() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("chatUnit");
    }

    @Override
    public void sendMessage(Message message) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction trx = entityManager.getTransaction();
        try {
            trx.begin();
            entityManager.persist(message);
            trx.commit();
        } catch (Exception e) {
            if (trx.isActive()) {
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
    public List<Message> getAllMessageByRecipientId(Long recipientId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Message> messages = null;
        try {
            messages = entityManager.createQuery("SELECT m FROM Message m WHERE m.receiver.id = :recipientId", Message.class)
                    .setParameter("recipientId", recipientId)
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
        EntityTransaction trx = entityManager.getTransaction();
        boolean isDeleted = false;
        try {
            trx.begin();
            Message message = entityManager.find(Message.class, id);
            if (message != null) {
                entityManager.remove(message);
                isDeleted = true;
            }
            trx.commit();
        } catch (Exception e) {
            if (trx.isActive()) {
                trx.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return isDeleted;
    }

    @Override
    public void updateMessage(Long id, String newContent) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction trx = entityManager.getTransaction();
        try {
            trx.begin();
            Message message = entityManager.find(Message.class, id);
            if (message != null) {
                message.setContent(newContent);
                entityManager.merge(message);
            }
            trx.commit();
        } catch (Exception e) {
            if (trx.isActive()) {
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
    public List<Message> getMessagesByGroup(int groupId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Message> messages = null;

        try {
            // Start a transaction for consistency
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            // Execute the query to get messages by group ID
            messages = entityManager.createQuery(
                            "SELECT m FROM Message m WHERE m.group.id = :groupId", Message.class)
                    .setParameter("groupId", groupId)
                    .getResultList();

            // Commit the transaction
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            entityManager.close();
        }

        return messages;
    }
}
