package com.Storchak.dao;

import com.Storchak.dto.FPTemplate;
import com.Storchak.dto.FPTemplateBranchingPoint;
import com.Storchak.dto.FPTemplateEndPoint;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class FPDao {

    private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;

    public FPDao() {
        entityManagerFactory = Persistence.createEntityManagerFactory("test");
    }

    public void updateFPTemplate(FPTemplate fpTemplate) {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(fpTemplate);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void createFPTemplate(FPTemplate fpTemplate) {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(fpTemplate);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void updateFPTemplateEnd(List<FPTemplateEndPoint> fpTemplateEndPoints) {
        for (FPTemplateEndPoint x : fpTemplateEndPoints) {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(x);
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    public void updateFPTemplateBranching(List<FPTemplateBranchingPoint> fpTemplateBranchingPoints) {
        for (FPTemplateBranchingPoint x : fpTemplateBranchingPoints) {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(x);
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    public void createFPTemplateEnd(List<FPTemplateEndPoint> fpTemplateEndPoints) {
        for (FPTemplateEndPoint x : fpTemplateEndPoints) {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(x);
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    public void createFPTemplateBranching(List<FPTemplateBranchingPoint> fpTemplateBranchingPoints) {
        for (FPTemplateBranchingPoint x : fpTemplateBranchingPoints) {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(x);
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    public List<FPTemplate> getTemplatesOfUser(long id) {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT f.id FROM FPTemplate f WHERE f.user.id=?1");
        query.setParameter(1, id);
        List<Long> templatesId = query.getResultList();
        List<FPTemplate> fpTemplates = new ArrayList();
        for (Long x : templatesId) {
            fpTemplates.add(entityManager.find(FPTemplate.class, x));
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return fpTemplates;
    }

    public void closeEntityManagerFactory() {
        entityManagerFactory.close();
    }
}
