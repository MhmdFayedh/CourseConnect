package sa.mhmdfayedh.CourseConnect.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import sa.mhmdfayedh.CourseConnect.common.mappers.CourseMapper;
import sa.mhmdfayedh.CourseConnect.common.mappers.UserMapper;
import sa.mhmdfayedh.CourseConnect.dto.CreateCourseRequestDTO;
import sa.mhmdfayedh.CourseConnect.dto.*;
import sa.mhmdfayedh.CourseConnect.enums.DifficultLevel;
import sa.mhmdfayedh.CourseConnect.enums.Language;
import sa.mhmdfayedh.CourseConnect.enums.Role;
import sa.mhmdfayedh.CourseConnect.models.Course;
import sa.mhmdfayedh.CourseConnect.models.User;
import sa.mhmdfayedh.CourseConnect.repositories.CourseDAO;
import sa.mhmdfayedh.CourseConnect.repositories.UserDAO;

import java.util.List;

@Service
public class CourseService {
    CourseDAO courseDAO;
    UserDAO userDAO;

    @Autowired
    public CourseService(CourseDAO courseDAO, UserDAO userDAO){
        this.courseDAO = courseDAO;
        this.userDAO = userDAO;
    }

    public CreateCourseResponseDTO createCourse(@Validated CreateCourseRequestDTO courseRequestDTO){
        if (courseRequestDTO == null){
            throw new IllegalArgumentException("Course cannot be null");
        }


        if (courseRequestDTO.getInstructor() == null){
            throw new IllegalArgumentException("Instructor information is missing from the course");
        }


        String language = Language.fromString(courseRequestDTO.getLanguage()).getDisplyName();
        String difficultLevel = DifficultLevel.fromString(courseRequestDTO.getDifficultLevel()).getDisplayName();
        int instructorId = courseRequestDTO.getInstructor().getId();
        User instructor = userDAO.findById(instructorId);

        if (instructor == null){
            throw new IllegalArgumentException("Cannot create a course without an instructor, the id passed :"
                    + instructorId);
        }

        courseRequestDTO.setLanguage(language);
        courseRequestDTO.setDifficultLevel(difficultLevel);
        courseRequestDTO.setInstructor(instructor);

        Course MappedCourse = CourseMapper.toEntity(courseRequestDTO);

        Course course = this.courseDAO.save(MappedCourse);

        CreateCourseResponseDTO dto = new CreateCourseResponseDTO();

        dto.setStatus("success");
        dto.setStatusCode(HttpStatus.CREATED.value());
        dto.setMessage("Course created successfully!");
        dto.setData(CourseMapper.toDTOList(List.of(course)));

        return dto;

    }


    public GetCourseResponseDTO findCourseById(int id){
        Course course = courseDAO.findById(id);

        GetCourseResponseDTO dto = new GetCourseResponseDTO();
        dto.setStatus("Success");
        dto.setStatusCode(HttpStatus.OK.value());
        dto.setMessage("Course retrieved successfully");
        dto.setData(CourseMapper.toDTOList(List.of(course)));

        return dto;
    }

    public GetCoursesResponseDTO findAllCourses(){
        List<Course> courses = courseDAO.findAll();


        GetCoursesResponseDTO dto = new GetCoursesResponseDTO();

        dto.setData(CourseMapper.toDTOList(courses));
        dto.setStatus("success");
        dto.setStatusCode(HttpStatus.OK.value());
        dto.setMessage("Courses Retrieved Successfully");
        dto.setTotal(courses.size());

        if (courses.isEmpty()){
            dto.setMessage("No Courses Found");
        }

        return dto;
    }


    public UpdateCourseResponseDTO updateCourse(UpdateCourseRequestDTO course){
        if (course == null){
            throw new IllegalArgumentException("Course cannot be null");
        }

        Course courseEntity = CourseMapper.toEntity(course);

        if (courseEntity.getInstructor() == null){
            throw new IllegalArgumentException("Instructor information is missing from the course");
        }

        int instructorId = courseEntity.getInstructor().getId();
        User instructor = userDAO.findById(instructorId);

        courseEntity.setInstructor(instructor);

        if (instructor == null){
            throw new IllegalArgumentException("There's no instructor with the id passed: " + instructorId);
        }

        if (!instructor.getRole().equals(Role.ROLE_INSTRUCTOR)){
            throw new IllegalArgumentException("User is not an instructor with the id passed: " + instructorId);
        }

        this.courseDAO.update(courseEntity);


        UpdateCourseResponseDTO dto = new UpdateCourseResponseDTO();
        dto.setStatus("success");
        dto.setStatusCode(HttpStatus.OK.value());
        dto.setMessage("Course updated successfully");
        dto.setData(CourseMapper.toDTOList(List.of(courseEntity)));

        return dto;
    }

    public DeleteCourseResponseDTO deleteCourse(int id){
        Course course = this.courseDAO.findById(id);

        if (course == null){
            throw new IllegalArgumentException("Course with ID "+ id + " does not exist.");
        }

        this.courseDAO.deleteById(id);

        DeleteCourseResponseDTO dto = new DeleteCourseResponseDTO();
        dto.setStatus("success");
        dto.setStatusCode(HttpStatus.OK.value());
        dto.setMessage("Course deleted successfully");

        return dto;
    }


    public RegisterCourseResponseDTO courseRegister(CourseRegisterRequestDTO requestDTO){
        if (requestDTO.getCourseId() == 0 || requestDTO.getStudentId() == 0){
            throw new IllegalArgumentException("Course/Student id  cannot be null");
        }

        Course course = courseDAO.findById(requestDTO.getCourseId());
        User student = userDAO.findById(requestDTO.getStudentId());


        if (course == null) {
            throw new IllegalArgumentException("Course not found with ID: " + requestDTO.getCourseId());
        }
        if (student == null){
            throw new IllegalArgumentException("Student not found with ID: " + requestDTO.getCourseId());
        }


        if (!student.getRole().equals(Role.ROLE_STUDENT)){
            throw new IllegalArgumentException("Only student can register a course");
        }



        student.addCourse(course);
        course.addUser(student);

        userDAO.update(student);

        RegisterCourseResponseDTO dto = new RegisterCourseResponseDTO();
        dto.setStatus("success");
        dto.setStatusCode(HttpStatus.OK.value());
        dto.setMessage("Student registered successfully for the course: " + course.getTitle());

        return dto;
    }


    public GetUsersResponseDTO getCourseAndStudents(int id){
        Course courseAndStudents = courseDAO.findCourseAndStudentsByCourseId(id);

        if (courseAndStudents == null){
            throw new IllegalArgumentException("Course not found");
        }

        if (courseAndStudents.getUsers().isEmpty()){
            throw new IllegalArgumentException("Course's students not found");
        }

        GetUsersResponseDTO dto = new GetUsersResponseDTO();

        dto.setStatus("success");
        dto.setStatusCode(HttpStatus.OK.value());
        dto.setMessage("Students retrieved successfully for course ID: " + id);
        dto.setData(UserMapper.toDTOList(courseAndStudents.getUsers()));
        dto.setTotal(courseAndStudents.getUsers().size());

        return dto;
    }


    public GetCoursesResponseDTO getCourseByTitleAndPrice(String title, Double price){
        List<Course> courses;


        if (title != null && price != null) {
            courses = this.courseDAO.findCourseByTitleAndPrice(title, price);
        } else if (price == null) {
            courses = this.courseDAO.findCourseByTitle(title);
        } else if (title == null) {
            courses = this.courseDAO.findCourseByPrice(price);
        } else {
            courses = this.courseDAO.findAll();
        }



        GetCoursesResponseDTO dto = new GetCoursesResponseDTO();
        dto.setStatus("success");
        dto.setStatusCode(HttpStatus.OK.value());
        dto.setMessage("Searched Courses Retrieved Successfully");
        dto.setData(CourseMapper.toDTOList(courses));
        dto.setTotal(courses.size());

        return dto;
    }


}
