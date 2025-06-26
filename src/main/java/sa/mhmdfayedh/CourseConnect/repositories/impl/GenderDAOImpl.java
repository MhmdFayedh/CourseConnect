package sa.mhmdfayedh.CourseConnect.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import sa.mhmdfayedh.CourseConnect.entities.Gender;
import sa.mhmdfayedh.CourseConnect.repositories.interfaces.GenderDAO;

import java.util.List;

@Repository
public class GenderDAOImpl implements GenderDAO {
    EntityManager entityManager;

    public GenderDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Gender findGenderById(int id) {
        TypedQuery<Gender> query = entityManager.createQuery(
                "SELECT g FROM Gender g WHERE g.id = :id AND g.isDeleted = false",
                Gender.class);

        query.setParameter("id", id);

        List<Gender> results = query.getResultList();


        if (results.isEmpty()){
            throw new RuntimeException("Gender not found with id: " + id);
        }

        return results.get(0);
    }
}
