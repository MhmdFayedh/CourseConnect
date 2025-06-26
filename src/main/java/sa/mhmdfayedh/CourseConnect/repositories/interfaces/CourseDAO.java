package sa.mhmdfayedh.CourseConnect.repositories.interfaces;

import sa.mhmdfayedh.CourseConnect.entities.Course;


import java.util.List;

public interface CourseDAO {
    List<Course> findAll(int pageNumber);
    List<Course> findAll(int pageNumber, String sort);
    Course findById(int id);
    Course save(Course course);
    void update(Course course);
    void deleteById(int id);
    Course findCourseAndStudentsByCourseId(int id);
    List<Course> findCourseByTitle(String title, int pageNumber);
    List<Course> findCourseByTitleAndPrice(String title, Double price, int pageNumber);
    List<Course> findCourseByPrice(double price, int pageNumber);
    Long countCourses();
}