package sa.mhmdfayedh.CourseConnect.services.v1.interfaces;

import sa.mhmdfayedh.CourseConnect.dto.v1.*;

public interface CourseService {
    CreateCourseResponseDTO createCourse(CreateCourseRequestDTO courseRequestDTO);
    GetCourseResponseDTO findCourseById(int id);
    GetCoursesResponseDTO findAllCourses(int pageNumber, String sort);
    UpdateCourseResponseDTO updateCourse(UpdateCourseRequestDTO course);
    DeleteCourseResponseDTO deleteCourse(int id);
    CourseEnrollmentResponseDTO courseEnrollment(CourseEnrollmentRequestDTO requestDTO);
    GetUsersResponseDTO getCourseAndStudents(int id, int pageNumber);
    GetCoursesResponseDTO getCourseByTitleAndPrice(int pageNumber,  String title, Double price);



}
