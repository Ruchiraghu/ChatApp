package org.com.chatapp.daoImpl;

import org.com.chatapp.dao.GroupDao;
import org.com.chatapp.entities.GroupChat;
import org.com.chatapp.entities.User;

import javax.persistence.*;
import java.util.List;

public class GroupDaoImpl implements GroupDao {
    private final EntityManagerFactory entityManagerFactory;

    public GroupDaoImpl() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("chatUnit");
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    @Override
    public void addGroup(GroupChat groupChat) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.persist(groupChat);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();  // Consider using a logger
        } finally {
            entityManager.close();
        }
    }

    @Override
    public GroupChat getGroup(Long id) {
        EntityManager entityManager = getEntityManager();
        GroupChat group;
        try {
            group = entityManager.find(GroupChat.class, id);
        } finally {
            entityManager.close();
        }
        return group;
    }

    @Override
    public GroupChat getGroupByName(String name) {
        EntityManager entityManager = getEntityManager();
        GroupChat group = null;
        try {
            group = entityManager.createQuery("SELECT g FROM GroupChat g WHERE g.name = :name", GroupChat.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Handle the case where no result is found, e.g., log or return null
        } finally {
            entityManager.close();
        }
        return group;
    }

    @Override
    public List<GroupChat> getAllGroups() {
        EntityManager entityManager = getEntityManager();
        List<GroupChat> groups;
        try {
            groups = entityManager.createQuery("SELECT g FROM GroupChat g", GroupChat.class).getResultList();
        } finally {
            entityManager.close();
        }
        return groups;
    }

    @Override
    public void updateGroup(GroupChat groupChat) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.merge(groupChat);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;  // Consider using a logger
        } finally {
            entityManager.close();
        }
    }

    public void addUsersToGroup(Long groupId, List<Long> userIds) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            GroupChat group = em.find(GroupChat.class, groupId);
            if (group != null) {
                for (Long userId : userIds) {
                    User user = em.find(User.class, userId);
                    if (user != null) {
                        group.getUsers().add(user);
                    }
                }
                em.merge(group); // Update the group with the new user list
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
