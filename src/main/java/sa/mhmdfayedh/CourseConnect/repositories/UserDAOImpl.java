package sa.mhmdfayedh.CourseConnect.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sa.mhmdfayedh.CourseConnect.models.User;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO{
    EntityManager entityManager;

    public UserDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> users = entityManager.createQuery("FROM User", User.class);

        return users.getResultList();
    }

    @Override
    public User findById(int id) {
        return entityManager.find(User.class, id);
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
                throw new RuntimeException("User not found by the id: " + user.getId());
            }
        }

        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }
}
