package org.com.chatapp.daoImpl;

import org.com.chatapp.dao.GroupDao;
import org.com.chatapp.entities.GroupChat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class GroupDaoImpl implements GroupDao {
    private final EntityManagerFactory entityManagerFactory;

    public GroupDaoImpl() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("chatUnit");
    }

    @Override
    public void addGroup(GroupChat group) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.persist(group);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public GroupChat getGroup(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        GroupChat group = null;
        try {
            group = entityManager.find(GroupChat.class, id);
        } finally {
            entityManager.close();
        }
        return group;
    }

    @Override
    public GroupChat getGroupByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        GroupChat group = null;
        try {
            group = entityManager.createQuery("SELECT g FROM Group g WHERE g.name = :name", GroupChat.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } finally {
            entityManager.close();
        }
        return group;
    }

    @Override
    public List<GroupChat> getAllGroups() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<GroupChat> groups = null;
        try {
            groups = entityManager.createQuery("SELECT g FROM Group g", GroupChat.class).getResultList();
        } finally {
            entityManager.close();
        }
        return groups;
    }
}
