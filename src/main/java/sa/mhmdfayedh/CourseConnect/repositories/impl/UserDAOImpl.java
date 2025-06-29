package sa.mhmdfayedh.CourseConnect.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sa.mhmdfayedh.CourseConnect.common.exceptions.UserNotFoundException;
import sa.mhmdfayedh.CourseConnect.entities.User;
import sa.mhmdfayedh.CourseConnect.repositories.interfaces.UserDAO;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    @Value("${app.api.config.pageSize}")
    private int pageSize;
    EntityManager entityManager;

    public UserDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> users = entityManager
                .createQuery("SELECT u FROM User u WHERE u.isDeleted = false", User.class);


        return users.getResultList();
    }

    @Override
    public List<User> findAll(int pageNumber) {
        TypedQuery<User> users = entityManager.createQuery("SELECT u FROM User u WHERE u.isDeleted = false", User.class)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize);

        return users.getResultList();
    }

    @Override
    public List<User> findAll(int pageNumber, String sort) {
        String[] sortParts = sort.split(",");
        String sortFieldInput = sortParts[0].trim().toLowerCase();
        String sortDirectionInput = sortParts.length > 1 ? sortParts[1].trim().toLowerCase() : "asc";



        String sortField = switch (sortFieldInput) {
            case "username" -> "u.username";
            case "email" -> "u.email";
            case "firstname" -> "u.firstName";
            case "lastname" -> "u.lastName";
            case "createdat" -> "u.createdAt";
            default -> "u.id";
        };

        String sortDirection = switch (sortDirectionInput) {
            case "desc" -> "DESC";
            default -> "ASC";
        };

        String baseQuery = "SELECT u FROM User u WHERE u.isDeleted = false ";
        String query = baseQuery + " ORDER BY " + sortField + " " + sortDirection;
        TypedQuery<User> users = entityManager.createQuery(query, User.class)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize);

        return users.getResultList();
    }

    @Override
    public User findById(int id) {

        User user = entityManager.find(User.class, id);
        if (user == null || user.isDeleted()){
            throw new UserNotFoundException("User not found with id: " + id);
        }

        return user;
    }

    @Override
    public User findByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.email = :email AND u.isDeleted = false",
                User.class);

        query.setParameter("email", email);

        List<User> results = query.getResultList();


        if (results.isEmpty()){
            return null;
        }


        return results.get(0);
    }

    @Override
    @Transactional
    public User save(User user) {
        entityManager.persist(user);

        return user;
    }

    @Override
    @Transactional
    public void update(User user) {
        if (user.getId() == 0 || !entityManager.contains(user)){
            User exexistingUser = entityManager.find(User.class, user.getId());
            if (exexistingUser == null){
                throw new UserNotFoundException("User not found by the id: " + user.getId());
            }
        }

        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        User user = entityManager.find(User.class, id);

        entityManager.createQuery("UPDATE User u " +
                "SET u.isDeleted = true, updatedAt = :updatedAt " +
                "WHERE u.id = :id")
                .setParameter("updatedAt", LocalDateTime.now())
                .setParameter("id", user.getId())
                .executeUpdate();
    }

    @Override
    public int countUsers() {
        return entityManager
                .createQuery("SELECT COUNT(u) FROM User u WHERE u.isDeleted = false", Long.class)
                .getSingleResult()
                .intValue();

    }

    @Override
    public boolean existsByEmail(String email) {
        Long count = entityManager
                .createQuery("SELECT COUNT(U) FROM User u WHERE u.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();

        return count > 0;
    }
}
