package org.com.chatapp.daoImpl;

import org.com.chatapp.dao.ChatRoomDao;
import org.com.chatapp.entities.ChatRoom;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class ChatRoomDaoImpl implements ChatRoomDao {
    private EntityManagerFactory entityManagerFactory;

    public ChatRoomDaoImpl() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("chatUnit");
    }

    @Override
    public void saveChatRoom(ChatRoom chatRoom) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction trx = null;
        try {
            trx = entityManager.getTransaction();
            trx.begin();
            entityManager.persist(chatRoom);
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
    public ChatRoom getChatRoomById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ChatRoom chatRoom = null;
        try {
            chatRoom = entityManager.find(ChatRoom.class, id);
        } finally {
            entityManager.close();
        }
        return chatRoom;
    }

    @Override
    public ChatRoom getChatRoomByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ChatRoom chatRoom = null;
        try {
            String jpql = "FROM ChatRoom WHERE name = :name";
            chatRoom = entityManager.createQuery(jpql, ChatRoom.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } finally {
            entityManager.close();
        }
        return chatRoom;
    }

    @Override
    public List<ChatRoom> getAllChatRooms() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<ChatRoom> chatRooms = null;
        try {
            String jpql = "FROM ChatRoom";
            chatRooms = entityManager.createQuery(jpql, ChatRoom.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
        return chatRooms;
    }

    @Override
    public void deleteChatRoom(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction trx = null;
        try {
            trx = entityManager.getTransaction();
            trx.begin();
            ChatRoom chatRoom = entityManager.find(ChatRoom.class, id);
            if (chatRoom != null) {
                entityManager.remove(chatRoom);
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
    public void updateChatRoom(ChatRoom chatRoom) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction trx = null;
        try {
            trx = entityManager.getTransaction();
            trx.begin();
            entityManager.merge(chatRoom);
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
}
