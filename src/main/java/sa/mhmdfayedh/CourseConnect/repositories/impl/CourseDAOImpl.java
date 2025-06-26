package sa.mhmdfayedh.CourseConnect.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import sa.mhmdfayedh.CourseConnect.common.exceptions.CourseNotFoundException;
import sa.mhmdfayedh.CourseConnect.entities.Course;
import sa.mhmdfayedh.CourseConnect.repositories.interfaces.CourseDAO;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CourseDAOImpl implements CourseDAO {
    @Value("${app.api.config.pageSize}")
    private int pageSize;
    private EntityManager entityManager;

    @Autowired
    public CourseDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<Course> findAll(int pageNumber) {
        TypedQuery<Course> course = entityManager.createQuery("SELECT c FROM Course c WHERE c.isDeleted = false", Course.class)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize);

        return course.getResultList();
    }

    @Override
    public List<Course> findAll(int pageNumber, String sort) {
        String[] sortParts = sort.split(",");
        String sortFieldInput = sortParts[0].trim().toLowerCase();
        String sortDirectionInput = sortParts.length > 1 ? sortParts[1].trim().toLowerCase() : "asc";

        String sortField = switch (sortFieldInput) {
            case "title" -> "c.title";
            case "price" -> "c.price";
            case "createdat" -> "c.createdAt";
            case "difficultylevel" -> "c.difficultyLevel";
            default -> "c.id";
        };

        String sortDirection = switch (sortDirectionInput) {
            case "desc" -> "DESC";
            default -> "ASC";
        };


        String baseQuery = "SELECT c FROM Course c WHERE c.isDeleted = false ";
        String query = baseQuery + " ORDER BY " + sortField + " " + sortDirection;

        TypedQuery<Course> course = entityManager.createQuery(query, Course.class)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize);

        return course.getResultList();
    }

    @Override
    public Course findById(int id) {

        TypedQuery<Course> query = entityManager.createQuery(
                "SELECT c FROM Course c WHERE c.id = :id AND c.isDeleted = false",
                Course.class);

        query.setParameter("id", id);

        List<Course> results = query.getResultList();


        if (results.isEmpty()){
            throw new CourseNotFoundException("Course not found with id: " + id);
        }


        return results.get(0);
    }

    @Override
    public Course save(Course course) {
        entityManager.persist(course);
        return course;
    }

    @Override
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
    public Course findCourseAndStudentsByCourseId(int id) {
        TypedQuery<Course> query = entityManager.createQuery(
                "SELECT c FROM Course c LEFT JOIN FETCH c.users WHERE c.id = :data", Course.class);

        query.setParameter("data", id);
        List<Course> course = query.getResultList();



        return course.isEmpty() ? null : course.get(0);
    }

    @Override
    public void deleteById(int id) {
        Course course = entityManager.find(Course.class, id);

        entityManager.createQuery("UPDATE Course c " +
                        "SET c.isDeleted = true, updatedAt = :updatedAt " +
                        "WHERE c.id = :id")
                        .setParameter("updatedAt", LocalDateTime.now())
                        .setParameter("id", course.getId())
                        .executeUpdate();
    }

    @Override
    public List<Course> findCourseByTitle(String title, int pageNumber) {
        TypedQuery<Course> courses = entityManager.createQuery("SELECT c FROM Course c " +
                "WHERE c.title LIKE :title", Course.class)
                .setFirstResult((pageNumber -1) * pageSize)
                .setMaxResults(pageSize);

        courses.setParameter("title", "%" + title + "%");

        return courses.getResultList();
    }

    @Override
    public List<Course> findCourseByTitleAndPrice(String title, Double price, int pageNumber) {
        TypedQuery<Course> courses = entityManager.createQuery("SELECT c FROM Course c WHERE c.title LIKE " +
                ":title AND c.price <= :price", Course.class)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize);

        courses.setParameter("title", "%" + title + "%");
        courses.setParameter("price", price);


        return courses.getResultList();
    }

    @Override
    public List<Course> findCourseByPrice(double price, int pageNumber) {
        TypedQuery<Course> courses = entityManager.createQuery("SELECT c FROM Course c " +
                "WHERE c.price <= :price", Course.class)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize);

        courses.setParameter("price", price);

        return courses.getResultList();
    }

    @Override
    public Long countCourses() {
        return entityManager.createQuery("SELECT COUNT(c) FROM Course c WHERE c.isDeleted = false", Long.class).getSingleResult();
    }


}
