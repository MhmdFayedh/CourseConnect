package sa.mhmdfayedh.CourseConnect.repositories;

import sa.mhmdfayedh.CourseConnect.models.Course;


import java.util.List;

public interface CourseDAO {
    List<Course> findAll();
    Course findById(int id);
    Course save(Course course);
    void update(Course course);
    void deleteById(int id);
    Course findCourseAndStudentsByCourseId(int id);
    List<Course> findCourseByTitle(String title);
    List<Course> findCourseByTitleAndPrice(String title, Double price);
    List<Course> findCourseByPrice(double price);
}