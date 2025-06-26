package sa.mhmdfayedh.CourseConnect.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import sa.mhmdfayedh.CourseConnect.entities.Role;
import sa.mhmdfayedh.CourseConnect.repositories.interfaces.RoleDAO;

import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {
    EntityManager entityManager;

    public RoleDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public Role findRoleById(int id) {
        TypedQuery<Role> query = entityManager.createQuery(
                "SELECT r FROM Role r WHERE r.id = :id AND r.isDeleted = false",
                Role.class);

        query.setParameter("id", id);

        List<Role> results = query.getResultList();


        if (results.isEmpty()){
            throw new RuntimeException("Role not found with id: " + id);
        }


        return results.get(0);
    }
}
