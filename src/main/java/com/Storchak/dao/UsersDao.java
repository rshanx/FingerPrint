package com.Storchak.dao;


import com.Storchak.dto.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class UsersDao {

    public EntityManagerFactory entityManagerFactory;
    public EntityManager entityManager;

    public UsersDao() {
        entityManagerFactory = Persistence.createEntityManagerFactory("test");
    }

    public void updateUser(User user) {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void createUser(User user) {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Long> getUsersId() {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT u.id FROM  User u");
        List<Long> usersId = query.getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return usersId;
    }

    public void closeEntityManagerFactory() {
        entityManagerFactory.close();
    }

}
