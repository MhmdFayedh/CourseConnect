package sa.mhmdfayedh.CourseConnect.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import sa.mhmdfayedh.CourseConnect.entities.DifficultyLevel;
import sa.mhmdfayedh.CourseConnect.repositories.interfaces.DifficultyLevelDAO;

import java.util.List;


@Repository
public class DifficultyLevelDAOImpl implements DifficultyLevelDAO {
    EntityManager entityManager;

    public DifficultyLevelDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public DifficultyLevel findDifficultyLevelById(int id) {
        TypedQuery<DifficultyLevel> query = entityManager.createQuery(
                "SELECT dl FROM DifficultyLevel dl WHERE dl.id = :id AND dl.isDeleted = false",
                DifficultyLevel.class);

        query.setParameter("id", id);

        List<DifficultyLevel> results = query.getResultList();


        if (results.isEmpty()){
            throw new RuntimeException("Difficulty level not found with id: " + id);
        }


        return results.get(0);
    }
}
