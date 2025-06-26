package sa.mhmdfayedh.CourseConnect.services.v1.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sa.mhmdfayedh.CourseConnect.common.exceptions.UserNotFoundException;
import sa.mhmdfayedh.CourseConnect.common.mappers.CourseMapper;
import sa.mhmdfayedh.CourseConnect.common.mappers.UserMapper;
import sa.mhmdfayedh.CourseConnect.dto.v1.*;
import sa.mhmdfayedh.CourseConnect.common.enums.RoleEnum;
import sa.mhmdfayedh.CourseConnect.common.exceptions.CourseNotFoundException;
import sa.mhmdfayedh.CourseConnect.entities.Course;
import sa.mhmdfayedh.CourseConnect.entities.DifficultyLevel;
import sa.mhmdfayedh.CourseConnect.entities.Language;
import sa.mhmdfayedh.CourseConnect.entities.User;
import sa.mhmdfayedh.CourseConnect.repositories.interfaces.CourseDAO;
import sa.mhmdfayedh.CourseConnect.repositories.interfaces.UserDAO;
import sa.mhmdfayedh.CourseConnect.services.v1.interfaces.CourseService;
import sa.mhmdfayedh.CourseConnect.services.v1.interfaces.DifficultyLevelService;
import sa.mhmdfayedh.CourseConnect.services.v1.interfaces.LanguageService;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
     private final CourseDAO courseDAO;
     private final UserDAO userDAO;
     private final LanguageService languageService;
     private final DifficultyLevelService difficultyLevelService;
     private final CacheManager cacheManager;

    @Autowired
    public CourseServiceImpl(CourseDAO courseDAO, UserDAO userDAO, LanguageService languageService, DifficultyLevelService difficultyLevelService, CacheManager cacheManager){
        this.courseDAO = courseDAO;
        this.userDAO = userDAO;
        this.languageService = languageService;
        this.difficultyLevelService = difficultyLevelService;
        this.cacheManager = cacheManager;
    }

    @Transactional
    public CreateCourseResponseDTO createCourse(CreateCourseRequestDTO courseRequestDTO) {
        if (courseRequestDTO == null){
            throw new IllegalArgumentException("Course cannot be null");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser  = userDAO.findByEmail(authentication.getName());

        if (courseRequestDTO.getInstructorId() != currentUser.getId()
                && !currentUser.getRole().getName().equals(RoleEnum.ROLE_ADMIN.getDisplyName())) {
            throw new IllegalArgumentException("You are not authorized to create a course for another instructor");
        }

        User instructor = userDAO.findById(courseRequestDTO.getInstructorId());
        if (instructor == null) {
            throw new UserNotFoundException("Instructor was not found");
        }
        Language language = languageService.findLanguageById(courseRequestDTO.getLanguageId());
        DifficultyLevel difficultyLevel = difficultyLevelService.findDifficultyLevelById(courseRequestDTO.getDifficultLevelId());

        if (!RoleEnum.ROLE_INSTRUCTOR.getDisplyName().equals(instructor.getRole().getName())) {
            throw new RuntimeException("User is not instructor");
        }


        Course mappedCourse = CourseMapper.toEntity(courseRequestDTO);
        mappedCourse.setLanguage(language);
        mappedCourse.setDifficultLevel(difficultyLevel);
        mappedCourse.setInstructor(instructor);


        Course course = this.courseDAO.save(mappedCourse);

        CreateCourseResponseDTO dto = new CreateCourseResponseDTO();

        dto.setStatus("success");
        dto.setStatusCode(HttpStatus.CREATED.value());
        dto.setMessage("Course created successfully!");
        dto.setData(CourseMapper.toDTOList(List.of(course)));

        return dto;
    }


    public GetCourseResponseDTO findCourseById(int id){

        Cache courseCache = cacheManager.getCache("courseCache");
        Course course = courseCache != null ? courseCache.get(id, Course.class) : null;


        if (course == null) {
            course = courseDAO.findById(id);
            if (courseCache != null) {
                courseCache.put(id, course);
            }
        }

        GetCourseResponseDTO dto = new GetCourseResponseDTO();
        dto.setStatus("success");
        dto.setStatusCode(HttpStatus.OK.value());
        dto.setMessage("Course retrieved successfully");
        dto.setData(CourseMapper.toDTOList(List.of(course)));

        return dto;
    }


    public GetCoursesResponseDTO findAllCourses(int pageNumber, String sort){
        List<Course> courses;
        Long CoursesCount = courseDAO.countCourses();

        if (sort != null && !sort.isBlank()) {
            courses = courseDAO.findAll(pageNumber, sort);
        } else {
            courses = courseDAO.findAll(pageNumber);
        }


        GetCoursesResponseDTO dto = new GetCoursesResponseDTO();
        PaginationDTO pagination = new PaginationDTO();

        String message = courses.isEmpty() ? "No courses found" : "Courses retrieved successfully";

        pagination.setSize(courses.size());
        pagination.setTotalElements(CoursesCount);
        pagination.setTotalPages((CoursesCount + 30 - 1 ) / 30);
        pagination.setPage(pageNumber);
        pagination.setFirst(pageNumber == 1);
        pagination.setLast(pageNumber == (CoursesCount + 30 - 1) / 30 );

        dto.setData(CourseMapper.toDTOList(courses));
        dto.setStatus("success");
        dto.setStatusCode(HttpStatus.OK.value());
        dto.setMessage(message);
        dto.setPagination(pagination);


        return dto;
    }

    @Transactional
    public UpdateCourseResponseDTO updateCourse(UpdateCourseRequestDTO course){
        if (course == null){
            throw new IllegalArgumentException("Course cannot be null");
        }

        Course coursePrev = courseDAO.findById(course.getId());

        if (coursePrev.getInstructor() == null) {
            throw new RuntimeException("This course has no instructor assigned. Cannot proceed with update.");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userDAO.findByEmail(authentication.getName());

        if (coursePrev.getInstructor().getId() != currentUser.getId()
                && !currentUser.getRole().getName().equals(RoleEnum.ROLE_ADMIN.getDisplyName())) {
            throw new RuntimeException("You are not authorized to update a course for another instructor");
        }

        if (course.getInstructorId() != currentUser.getId()
                && !currentUser.getRole().getName().equals(RoleEnum.ROLE_ADMIN.getDisplyName())) {
            throw new RuntimeException("You are not authorized to update a course to another instructor");

        }

        Language language = languageService.findLanguageById(course.getLanguageId());
        DifficultyLevel difficultyLevel = difficultyLevelService.findDifficultyLevelById(course.getDifficultLevelId());

        Course courseEntity = CourseMapper.toEntity(course);

        courseEntity.setInstructor(currentUser);
        courseEntity.setLanguage(language);
        courseEntity.setDifficultLevel(difficultyLevel);
        courseEntity.setCreatedAt(coursePrev.getCreatedAt());


        if (!currentUser.getRole().getName().equals(RoleEnum.ROLE_INSTRUCTOR.getDisplyName())){
            throw new IllegalArgumentException("User is not an instructor with the id passed: " + courseEntity.getInstructor().getId());
        }

        this.courseDAO.update(courseEntity);

        Cache cache = cacheManager.getCache("courseCache");
        if (cache != null){
            cache.put(courseEntity.getId(), courseEntity);
        }

        UpdateCourseResponseDTO dto = new UpdateCourseResponseDTO();
        dto.setStatus("success");
        dto.setStatusCode(HttpStatus.OK.value());
        dto.setMessage("Course updated successfully");
        dto.setData(CourseMapper.toDTOList(List.of(courseEntity)));

        return dto;
    }

    @Transactional
    public DeleteCourseResponseDTO deleteCourse(int id){
        Course course = this.courseDAO.findById(id);
        if (course == null){
            throw new CourseNotFoundException("Course with ID "+ id + " does not exist.");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser  = userDAO.findByEmail(authentication.getName());


        if (course.getInstructor().getId() != currentUser.getId()
                && !currentUser.getRole().getName().equals(RoleEnum.ROLE_ADMIN.getDisplyName())) {
            throw new RuntimeException("You are not authorized to delete a course for another instructor");
        }

        this.courseDAO.deleteById(id);

        DeleteCourseResponseDTO dto = new DeleteCourseResponseDTO();
        dto.setStatus("success");
        dto.setStatusCode(HttpStatus.OK.value());
        dto.setMessage("Course deleted successfully");

        return dto;
    }

    @Transactional
    public CourseEnrollmentResponseDTO courseEnrollment(CourseEnrollmentRequestDTO requestDTO){
        if (requestDTO.getCourseId() == 0 || requestDTO.getStudentId() == 0){
            throw new IllegalArgumentException("Course/Student id  cannot be null");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser  = userDAO.findByEmail(authentication.getName());




        Course course = courseDAO.findById(requestDTO.getCourseId());
        User student = userDAO.findById(requestDTO.getStudentId());

        if (course == null) {
            throw new CourseNotFoundException("Course not found with ID: " + requestDTO.getCourseId());
        }
        if (student == null){
            throw new UserNotFoundException("Student not found with ID: " + requestDTO.getCourseId());
        }

        if (student.getId() != currentUser.getId()
                && !currentUser.getRole().getName().equals(RoleEnum.ROLE_ADMIN.getDisplyName())) {
            throw new RuntimeException("You are not authorized to register a course for another stduent");
        }



        if (!student.getRole().getName().equals(RoleEnum.ROLE_STUDENT.getDisplyName())){
            throw new IllegalArgumentException("Only student can register a course");
        }



        student.addCourse(course);
        course.addUser(student);

        userDAO.update(student);

        CourseEnrollmentResponseDTO dto = new CourseEnrollmentResponseDTO();
        dto.setStatus("success");
        dto.setStatusCode(HttpStatus.OK.value());
        dto.setMessage("Student registered successfully for the course: " + course.getTitle());

        return dto;
    }


    public GetUsersResponseDTO getCourseAndStudents(int id, int pageNumber){
        Course courseAndStudents = courseDAO.findCourseAndStudentsByCourseId(id);

        if (courseAndStudents == null){
            System.out.println("State of CourseAndStudents: " + courseAndStudents);
            throw new CourseNotFoundException("Course not found");
        }

        if (courseAndStudents.getUsers().isEmpty()){
            throw new UserNotFoundException("Course's students not found");
        }

        GetUsersResponseDTO dto = new GetUsersResponseDTO();
        PaginationDTO pagination = new PaginationDTO();


        pagination.setSize(courseAndStudents.getUsers().size());
        pagination.setTotalElements(courseAndStudents.getUsers().size());
        pagination.setTotalPages((courseAndStudents.getUsers().size() + 30 - 1 ) / 30);
        pagination.setPage(pageNumber);
        pagination.setFirst(pageNumber == 1);
        pagination.setLast(pageNumber == (courseAndStudents.getUsers().size() + 30 - 1) / 30 );


        dto.setStatus("success");
        dto.setStatusCode(HttpStatus.OK.value());
        dto.setMessage("Students retrieved successfully for course ID: " + id);
        dto.setPagination(pagination);
        dto.setData(UserMapper.toDTOList(courseAndStudents.getUsers()));

        return dto;
    }


    public GetCoursesResponseDTO getCourseByTitleAndPrice(int pageNumber,  String title, Double price){
        List<Course> courses;


        if (title != null && price != null) {
            courses = this.courseDAO.findCourseByTitleAndPrice(title, price, pageNumber);
        } else if (price == null) {
            courses = this.courseDAO.findCourseByTitle(title, pageNumber);
        } else if (title == null) {
            courses = this.courseDAO.findCourseByPrice(price, pageNumber);
        } else {
            courses = this.courseDAO.findAll(pageNumber);
        }



        GetCoursesResponseDTO dto = new GetCoursesResponseDTO();
        PaginationDTO pagination = new PaginationDTO();

        pagination.setSize(courses.size());
        pagination.setTotalElements(courses.size());
        pagination.setTotalPages((courses.size() + 30 - 1 ) / 30);
        pagination.setPage(pageNumber);
        pagination.setFirst(pageNumber == 1);
        pagination.setLast(pageNumber == (courses.size() + 30 - 1) / 30);



        dto.setStatus("success");
        dto.setStatusCode(HttpStatus.OK.value());
        dto.setMessage("Searched Courses Retrieved Successfully");
        dto.setData(CourseMapper.toDTOList(courses));
        dto.setPagination(pagination);


        return dto;
    }


}
