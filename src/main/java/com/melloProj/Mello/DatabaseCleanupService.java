package com.melloProj.Mello;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import jakarta.persistence.PersistenceContext;
@Service
public class DatabaseCleanupService {

    @PersistenceContext
    private EntityManager entityManager;

    public void deleteAllProjects() {
        entityManager.createNativeQuery("DROP TABLE IF EXISTS project").executeUpdate();
    }
}
