package sa.mhmdfayedh.CourseConnect.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import sa.mhmdfayedh.CourseConnect.entities.Language;
import sa.mhmdfayedh.CourseConnect.repositories.interfaces.LanguageDAO;

import java.util.List;

@Repository
public class LanguageDAOImpl implements LanguageDAO {
    EntityManager entityManager;

    public LanguageDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Language findLanguageById(int id) {
        TypedQuery<Language> query = entityManager.createQuery(
                "SELECT l FROM Language l WHERE l.id = :id AND l.isDeleted = false",
                Language.class);

        query.setParameter("id", id);

        List<Language> results = query.getResultList();


        if (results.isEmpty()){
            throw new RuntimeException("Language not found with id: " + id);
        }


        return results.get(0);
    }
}
