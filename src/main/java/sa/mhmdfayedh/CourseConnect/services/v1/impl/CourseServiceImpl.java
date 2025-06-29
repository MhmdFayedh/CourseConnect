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
import sa.mhmdfayedh.CourseConnect.common.utils.AuthUtil;
import sa.mhmdfayedh.CourseConnect.common.utils.PaginationUtils;
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
     private final AuthUtil authUtil;

    @Autowired
    public CourseServiceImpl(CourseDAO courseDAO,
                             UserDAO userDAO,
                             LanguageService languageService,
                             DifficultyLevelService difficultyLevelService,
                             CacheManager cacheManager,
                             AuthUtil authUtil){
        this.courseDAO = courseDAO;
        this.userDAO = userDAO;
        this.languageService = languageService;
        this.difficultyLevelService = difficultyLevelService;
        this.cacheManager = cacheManager;
        this.authUtil = authUtil;
    }

    @Transactional
    public ResponseDTO<CourseDTO> createCourse(CreateCourseRequestDTO courseRequestDTO) {
        if (courseRequestDTO == null){
            throw new IllegalArgumentException("Course cannot be null");
        }

        authUtil.requireSelfOrAdmin(courseRequestDTO.getInstructorId(), "You are not authorized to create a course for another instructor");


        User instructor = userDAO.findById(courseRequestDTO.getInstructorId());

        if (instructor == null) {
            throw new UserNotFoundException("Instructor was not found");
        }

        Language language = languageService.findLanguageById(courseRequestDTO.getLanguageId());
        DifficultyLevel difficultyLevel = difficultyLevelService.findDifficultyLevelById(courseRequestDTO.getDifficultLevelId());

        if (!RoleEnum.ROLE_INSTRUCTOR.getDisplyName().equals(instructor.getRole().getName())) {
            throw new IllegalArgumentException("User is not instructor");
        }


        Course mappedCourse = CourseMapper.toEntity(courseRequestDTO);
        mappedCourse.setLanguage(language);
        mappedCourse.setDifficultLevel(difficultyLevel);
        mappedCourse.setInstructor(instructor);


        Course course = this.courseDAO.save(mappedCourse);

        return ResponseDTO
                .<CourseDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Course created successfully!")
                .data(CourseMapper.toDTOList(List.of(course)))
                .build();

    }


    public ResponseDTO<CourseDTO> findCourseById(int id){

        Cache courseCache = cacheManager.getCache("courseCache");
        Course course = courseCache != null ? courseCache.get(id, Course.class) : null;


        if (course == null) {
            course = courseDAO.findById(id);
            if (courseCache != null) {
                courseCache.put(id, course);
            }
        }

        return ResponseDTO
                .<CourseDTO>builder()
                .message("Course retrieved successfully")
                .data(CourseMapper.toDTOList(List.of(course)))
                .build();

    }


    public ResponseDTO<CourseDTO> findAllCourses(int pageNumber, String sort){
        List<Course> courses;
        int coursesCount = courseDAO.countCourses();

        if (sort != null && !sort.isBlank()) {
            courses = courseDAO.findAll(pageNumber, sort);
        } else {
            courses = courseDAO.findAll(pageNumber);
        }




        String message = courses.isEmpty() ? "No courses found" : "Courses retrieved successfully";


        PaginationDTO pagination = PaginationUtils
                .createPagination(courses,
                        coursesCount,
                        pageNumber,
                        30);

        return ResponseDTO
                .<CourseDTO>builder()
                .message(message)
                .data(CourseMapper.toDTOList(courses))
                .pagination(pagination)
                .build();
    }

    @Transactional
    public ResponseDTO<CourseDTO> updateCourse(UpdateCourseRequestDTO course){
        if (course == null){
            throw new IllegalArgumentException("Course cannot be null");
        }

        Course coursePrev = courseDAO.findById(course.getId());

        if (coursePrev.getInstructor() == null) {
            throw new RuntimeException("This course has no instructor assigned. Cannot proceed with update.");
        }

        authUtil.requireSelfOrAdmin(coursePrev.getInstructor().getId(),
                "You are not authorized to update a course for another instructor");

        authUtil.requireSelfOrAdmin(course.getInstructorId(),
                "You are not authorized to update a course to another instructor");

        Language language = languageService.findLanguageById(course.getLanguageId());
        DifficultyLevel difficultyLevel = difficultyLevelService.findDifficultyLevelById(course.getDifficultLevelId());

        Course courseEntity = CourseMapper.toEntity(course);

        courseEntity.setInstructor(authUtil.getCurrentUser());
        courseEntity.setLanguage(language);
        courseEntity.setDifficultLevel(difficultyLevel);
        courseEntity.setCreatedAt(coursePrev.getCreatedAt());

        this.courseDAO.update(courseEntity);

        Cache cache = cacheManager.getCache("courseCache");
        if (cache != null){
            cache.put(courseEntity.getId(), courseEntity);
        }

        return ResponseDTO
                .<CourseDTO>builder()
                .message("Course updated successfully")
                .data(CourseMapper.toDTOList(List.of(courseEntity)))
                .build();

    }

    @Transactional
    public ResponseDTO<CourseDTO> deleteCourse(int id){
        Course course = this.courseDAO.findById(id);
        if (course == null){
            throw new CourseNotFoundException("Course with ID "+ id + " does not exist.");
        }

        authUtil.requireSelfOrAdmin(course.getInstructor().getId(),
                "You are not authorized to delete a course for another instructor");

        this.courseDAO.deleteById(id);

        Cache cache = cacheManager.getCache("courseCache");
        if (cache != null) {
            cache.evict(id);
        }

        return ResponseDTO.<CourseDTO>builder().message("Course deleted successfully").build();
    }

    @Transactional
    public ResponseDTO<CourseDTO> courseEnrollment(CourseEnrollmentRequestDTO requestDTO){
        Course course = courseDAO.findById(requestDTO.getCourseId());
        User student = userDAO.findById(requestDTO.getStudentId());

        if (course == null || student == null) {
            if (course == null) {
                throw new CourseNotFoundException("Course not found with ID: " + requestDTO.getCourseId());
            }
            throw new UserNotFoundException("Student not found with ID: " + requestDTO.getStudentId());
        }

        authUtil.requireSelfOrAdmin(student.getId(),
                "You are not authorized to register a course for another student");

        student.addCourse(course);
        course.addUser(student);

        userDAO.update(student);

        return ResponseDTO
                .<CourseDTO>builder()
                .message("Student registered successfully for the course: " + course.getTitle())
                .build();
    }


    public ResponseDTO<UserDTO> getCourseAndStudents(int id, int pageNumber){
        Course courseAndStudents = courseDAO.findCourseAndStudentsByCourseId(id);

        if (courseAndStudents == null){
            throw new CourseNotFoundException("Course not found");
        }

        if (courseAndStudents.getUsers().isEmpty()){
            throw new UserNotFoundException("Course's students not found");
        }


        return ResponseDTO
                .<UserDTO>builder()
                .message("Students retrieved successfully for course ID: " + id)
                .data(UserMapper.toDTOList(courseAndStudents.getUsers()))
                .build();
    }


    public ResponseDTO<CourseDTO> getCourseByTitleAndPrice(int pageNumber,  String title, Double price){
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

       return ResponseDTO
               .<CourseDTO>builder()
                .message("Searched Courses Retrieved Successfully")
                .data(CourseMapper.toDTOList(courses))
               .pagination(null)
               .build();

    }


}
