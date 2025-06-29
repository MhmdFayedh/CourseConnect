package sa.mhmdfayedh.CourseConnect.repositories.interfaces;

import sa.mhmdfayedh.CourseConnect.entities.User;

import java.util.List;

public interface UserDAO {
    List<User> findAll();
    List<User> findAll(int pageNumber);
    List<User> findAll(int pageNumber, String sort);
    User findById(int id);
    User findByEmail(String username);
    User save(User user);
    void update(User user);
    void deleteById(int id);
    int countUsers();
    boolean existsByEmail(String email);
}
