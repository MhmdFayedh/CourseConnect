package sa.mhmdfayedh.CourseConnect.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sa.mhmdfayedh.CourseConnect.models.Course;

import java.util.List;

@Repository
public class CourseDAOImpl implements CourseDAO {
    private EntityManager entityManager;

    @Autowired
    public CourseDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<Course> findAll() {
        TypedQuery<Course> courses = entityManager.createQuery("FROM Course", Course.class);

        return courses.getResultList();
    }

    @Override
    public Course findById(int id) {
        return entityManager.find(Course.class,id);
    }

    @Override
    @Transactional
    public Course save(Course course) {
        entityManager.persist(course);
        return course;
    }

    @Override
    @Transactional
    public void update(Course course) {
        if (course.getId() == 0 || !entityManager.contains(course)){
            Course exexistingCourse = entityManager.find(Course.class, course.getId());
            if (exexistingCourse == null){
                throw new RuntimeException("Course not found by the id: " + course.getId());
            }
        }

        entityManager.merge(course);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Course course = entityManager.find(Course.class, id);
        entityManager.remove(course);
    }

    @Override
    public Course findCourseAndStudentsByCourseId(int id) {
        TypedQuery<Course> query = entityManager.createQuery("SELECT c from Course c " +
                "JOIN FETCH c.users " +
                "WHERE c.id = :data", Course.class);

        query.setParameter("data", id);
        Course course = query.getSingleResult();


        return course;

    }

    @Override
    public List<Course> findCourseByTitle(String title) {
        TypedQuery<Course> courses = entityManager.createQuery("SELECT c FROM Course c " +
                "WHERE c.title LIKE :data", Course.class);

        courses.setParameter("data", "%" + title + "%");

        return courses.getResultList();
    }

    @Override
    public List<Course> findCourseByTitleAndPrice(String title, Double price) {
        TypedQuery<Course> courses = entityManager.createQuery("SELECT c FROM Course c WHERE c.title LIKE " +
                ":title AND c.price <= :price", Course.class);

        courses.setParameter("title", "%" + title + "%");
        courses.setParameter("price", price);


        return courses.getResultList();
    }

    @Override
    public List<Course> findCourseByPrice(double price) {
        TypedQuery<Course> courses = entityManager.createQuery("SELECT c FROM Course c " +
                "WHERE c.price <= :data", Course.class);

        courses.setParameter("data", price);

        return courses.getResultList();
    }


}
