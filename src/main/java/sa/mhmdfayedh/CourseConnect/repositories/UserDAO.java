package sa.mhmdfayedh.CourseConnect.repositories;

import sa.mhmdfayedh.CourseConnect.models.User;

import java.util.List;

public interface UserDAO {
    List<User> findAll();
    User findById(int id);
    User save(User user);
    void update(User user);
    void deleteById(int id);
}
