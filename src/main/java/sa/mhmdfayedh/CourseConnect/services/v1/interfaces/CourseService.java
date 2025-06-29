package sa.mhmdfayedh.CourseConnect.services.v1.interfaces;

import sa.mhmdfayedh.CourseConnect.dto.v1.*;

public interface CourseService {
    ResponseDTO<CourseDTO> createCourse(CreateCourseRequestDTO courseRequestDTO);
    ResponseDTO<CourseDTO> findCourseById(int id);
    ResponseDTO<CourseDTO> findAllCourses(int pageNumber, String sort);
    ResponseDTO<CourseDTO> updateCourse(UpdateCourseRequestDTO course);
    ResponseDTO<CourseDTO> deleteCourse(int id);
    ResponseDTO<CourseDTO> courseEnrollment(CourseEnrollmentRequestDTO requestDTO);
    ResponseDTO<UserDTO> getCourseAndStudents(int id, int pageNumber);
    ResponseDTO<CourseDTO> getCourseByTitleAndPrice(int pageNumber,  String title, Double price);



}
