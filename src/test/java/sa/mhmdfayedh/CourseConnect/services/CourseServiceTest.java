package sa.mhmdfayedh.CourseConnect.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import sa.mhmdfayedh.CourseConnect.common.exceptions.UserNotFoundException;
import sa.mhmdfayedh.CourseConnect.common.mappers.CourseMapper;
import sa.mhmdfayedh.CourseConnect.common.utils.AuthUtil;
import sa.mhmdfayedh.CourseConnect.dto.v1.*;
import sa.mhmdfayedh.CourseConnect.common.enums.DifficultLevelEnum;
import sa.mhmdfayedh.CourseConnect.common.enums.LanguageEnum;
import sa.mhmdfayedh.CourseConnect.common.exceptions.CourseNotFoundException;
import sa.mhmdfayedh.CourseConnect.entities.Course;
import sa.mhmdfayedh.CourseConnect.entities.DifficultyLevel;
import sa.mhmdfayedh.CourseConnect.entities.Role;
import sa.mhmdfayedh.CourseConnect.entities.User;
import sa.mhmdfayedh.CourseConnect.repositories.interfaces.CourseDAO;
import sa.mhmdfayedh.CourseConnect.repositories.interfaces.DifficultyLevelDAO;
import sa.mhmdfayedh.CourseConnect.repositories.interfaces.LanguageDAO;
import sa.mhmdfayedh.CourseConnect.repositories.interfaces.UserDAO;
import sa.mhmdfayedh.CourseConnect.services.v1.impl.CourseServiceImpl;
import sa.mhmdfayedh.CourseConnect.services.v1.impl.DifficultyLevelServiceImpl;
import sa.mhmdfayedh.CourseConnect.services.v1.impl.LanguageServiceImpl;
import sa.mhmdfayedh.CourseConnect.services.v1.interfaces.CourseService;
import sa.mhmdfayedh.CourseConnect.services.v1.interfaces.DifficultyLevelService;
import sa.mhmdfayedh.CourseConnect.services.v1.interfaces.LanguageService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private CourseDAO courseDAO;
    @Mock
    private UserDAO userDAO;
    @Mock
    private LanguageDAO languageDAO;
    @Mock
    private DifficultyLevelDAO difficultyLevelDAO;
    @Mock
    private CacheManager cacheManager;
    @Mock
    private AuthUtil authUtil;

    private LanguageService languageService;
    private DifficultyLevelService difficultyLevelService;
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

         languageService = new LanguageServiceImpl(languageDAO);
         difficultyLevelService = new DifficultyLevelServiceImpl(difficultyLevelDAO);
         courseService = new CourseServiceImpl(courseDAO, userDAO, languageService, difficultyLevelService, cacheManager, authUtil);

    }


    public CourseServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    /*
     * createCourse method
     *
     * TESTS:
     * */

    @Test
    void shouldReturnCreateCourseResponseSuccessfully() {
        // Arrange

        Role role = new Role();
        role.setName("ROLE_INSTRUCTOR"); // Use RoleEnum
        role.setDeleted(false);

        sa.mhmdfayedh.CourseConnect.entities.Language language = new sa.mhmdfayedh.CourseConnect.entities.Language();
        DifficultyLevel difficultyLevel = new DifficultyLevel();

        language.setId(1);
        language.setName("ENGLISH");
        difficultyLevel.setId(1);
        difficultyLevel.setName("HARD");


        User instructor = new User();
        instructor.setId(1);
        instructor.setFirstName("John");
        instructor.setRole(role);


        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(user.getEmail());

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        when(userDAO.findByEmail(user.getEmail())).thenReturn(user);

        CreateCourseRequestDTO requestDTO = new CreateCourseRequestDTO();
        requestDTO.setTitle("Test Course");
        requestDTO.setDescription("This is a test course.");
        requestDTO.setStartAt(LocalDateTime.of(2025, 7, 1, 10, 0));
        requestDTO.setEndAt(LocalDateTime.of(2025, 8, 1, 10, 0));
        requestDTO.setSlug("test-course");
        requestDTO.setDifficultLevelId(1);
        requestDTO.setLanguageId(1);
        requestDTO.setPrice(100.0);
        requestDTO.setInstructorId(1);


        when(userDAO.findById(1)).thenReturn(instructor);
        when(languageService.findLanguageById(1)).thenReturn(language);
        when(difficultyLevelService.findDifficultyLevelById(1)).thenReturn(difficultyLevel);


        Course mappedCourse = CourseMapper.toEntity(requestDTO);
        mappedCourse.setLanguage(language);
        mappedCourse.setDifficultLevel(difficultyLevel);
        mappedCourse.setInstructor(instructor);
        when(courseDAO.save(any(Course.class))).thenReturn(mappedCourse);

        ResponseDTO<CourseDTO> response = courseService.createCourse(requestDTO);


        assertNotNull(response);
        assertEquals("success", response.getStatus());
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        assertEquals("Course created successfully!", response.getMessage());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().size());
        assertEquals("Test Course", response.getData().get(0).getTitle());


        verify(userDAO, times(1)).findById(1);
        verify(courseDAO, times(1)).save(any(Course.class));
    }

    @Test
    void shouldThrowIllegalArgumentException_WithNullRequest(){
        assertThrows(IllegalArgumentException.class, () -> courseService.createCourse(null));
    }



    @Test
    void shouldThrowIllegalArgumentException_WhenNoUserMatch() {
        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(user.getEmail());

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        when(userDAO.findByEmail(user.getEmail())).thenReturn(user);

        when(userDAO.findById(1)).thenReturn(null);


        CreateCourseRequestDTO courseRequest = new CreateCourseRequestDTO(
                "Java Fundamentals",
                "A beginner-friendly Java course",
                LocalDateTime.of(2025, 6, 10, 10, 0),
                LocalDateTime.of(2025, 7, 10, 10, 0),
                "java-fundamentals",
                1,
                1,
                49.99,
                1
        );
        assertThrows(UserNotFoundException.class, () -> courseService.createCourse(courseRequest) );
    }

    /*
     * findAllCourses method
     *
     * TESTS:
     * */
    @Test
    void shouldReturnCoursesAndMetadata_whenCoursesExist() {
        List<Course> courses = generateCourses(5);

        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(user.getEmail());

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        when(userDAO.findByEmail(user.getEmail())).thenReturn(user);



        when(courseDAO.findAll(1)).thenReturn(courses);
        when(courseDAO.countCourses()).thenReturn(courses.size());

        ResponseDTO<CourseDTO> responseDTO = courseService.findAllCourses(1, "");

        assertEquals("success", responseDTO.getStatus());
        assertEquals(200, responseDTO.getStatusCode());
        assertEquals("Courses retrieved successfully", responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
    }

    @Test
    void shouldReturnCoursesAndMetadata_withoutCourses() {
        List<Course> courses = new ArrayList<>();

        when(courseDAO.findAll(1)).thenReturn(courses);

        ResponseDTO<CourseDTO> responseDTO = courseService.findAllCourses(1, "");

        assertEquals("success", responseDTO.getStatus());
        assertEquals(200, responseDTO.getStatusCode());
        assertEquals("No courses found", responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
        assertTrue(responseDTO.getData().isEmpty());

    }


    /*
     * findCourseById method
     *
     * TESTS:
     * */

    @Test
    void shouldReturnGetCourseResponseDTO_WithValidCourse() {
        List<Course> courses = generateCourses(1);
        Course course = courses.get(0);
        course.setId(1);

        when(courseDAO.findById(1)).thenReturn(course);

        ResponseDTO<CourseDTO> responseDTO = courseService.findCourseById(1);

        assertEquals("success", responseDTO.getStatus());
        assertEquals("Course retrieved successfully", responseDTO.getMessage());
        assertEquals(200, responseDTO.getStatusCode());
        assertEquals(1, responseDTO.getData().get(0).getId());
        assertNotNull(responseDTO);
    }

    @Test
    void shouldThrowCourseNotFoundException_withNoCourseFound() {
        when(courseDAO.findById(1)).thenThrow(new CourseNotFoundException("Course not found"));

        assertThrows(CourseNotFoundException.class, () -> courseService.findCourseById(1));
    }
    /*
     * updateCourse method
     *
     * TESTS:
     * */

    @Test
    void shouldUpdateCourseSuccessfully_whenValidRequestIsProvided() {
        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(user.getEmail());

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        when(userDAO.findByEmail(user.getEmail())).thenReturn(user);

        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_INSTRUCTOR");

        sa.mhmdfayedh.CourseConnect.entities.Language language = new sa.mhmdfayedh.CourseConnect.entities.Language();
        language.setName("ENGLISH");
        language.setDeleted(false);

        DifficultyLevel difficultyLevel = new DifficultyLevel();
        difficultyLevel.setName("HARD");
        difficultyLevel.setDeleted(false);

        User instructor = new User();
        instructor.setId(1);
        instructor.setRole(role);

        user.setRole(role);

        List<Course> courses = generateCourses(1);
        Course course = courses.get(0);
        course.setInstructor(instructor);
        course.setId(1);

        UpdateCourseRequestDTO requestDTO = new UpdateCourseRequestDTO();
        requestDTO.setId(1);
        requestDTO.setTitle("Updated Java Basics");
        requestDTO.setDescription("An updated description of the Java basics course.");
        requestDTO.setStartAt(LocalDateTime.of(2025, 7, 5, 10, 0));
        requestDTO.setEndAt(LocalDateTime.of(2025, 8, 5, 10, 0));
        requestDTO.setSlug("updated-java-basics");
        requestDTO.setLanguageId(1);
        requestDTO.setDifficultLevelId(1);
        requestDTO.setPrice(175.0);
        requestDTO.setInstructorId(1);

        when(courseDAO.findById(1)).thenReturn(course);
        when(userDAO.findById(1)).thenReturn(instructor);
        when(languageService.findLanguageById(1)).thenReturn(language);
        when(difficultyLevelService.findDifficultyLevelById(1)).thenReturn(difficultyLevel);
        ResponseDTO<CourseDTO> responseDTO = courseService.updateCourse(requestDTO);

        assertEquals("success", responseDTO.getStatus());
        assertEquals(200, responseDTO.getStatusCode());
        assertEquals("Course updated successfully", responseDTO.getMessage());
        assertEquals("Updated Java Basics", responseDTO.getData().get(0).getTitle());
    }

    @Test
    void shouldThrowIllegalArgumentException_whenCourseRequestNull(){
        assertThrows(IllegalArgumentException.class, () -> courseService.updateCourse(null));
    }

    /*
     * deleteCourse method
     *
     * TESTS:
     * */

    @Test
    void shouldDeleteCourseSuccessfully_WhenValidIdProvided() {
        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(user.getEmail());

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        when(userDAO.findByEmail(user.getEmail())).thenReturn(user);

        int courseId = 1;
        Course course = new Course();
        course.setInstructor(user);
        course.setId(courseId);

        when(courseDAO.findById(courseId)).thenReturn(course);
        doNothing().when(courseDAO).deleteById(courseId);

        ResponseDTO<CourseDTO> responseDTO = courseService.deleteCourse(courseId);

        assertNotNull(responseDTO);
        assertEquals("success", responseDTO.getStatus());
        assertEquals(200, responseDTO.getStatusCode());
        assertEquals("Course deleted successfully", responseDTO.getMessage());

        verify(courseDAO, times(1)).deleteById(courseId);
    }

    @Test
    void shouldThrowCourseNotFoundException_whenCourseNotFound() {
        when(courseDAO.findById(1)).thenReturn(null);
        assertThrows(CourseNotFoundException.class, () -> courseService.deleteCourse(1));
    }


    private List<Course> generateCourses(int count) {
        List<Course> courses = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            String title = "Course " + i;
            String description = "Description for Course " + i;
            LocalDateTime startAt = LocalDateTime.now().plusDays(i);
            LocalDateTime endAt = startAt.plusMonths(1);
            String slug = "course-" + i;
            DifficultLevelEnum level = (i % 3 == 0) ? DifficultLevelEnum.HARD : DifficultLevelEnum.EAZY;
            LanguageEnum language = LanguageEnum.ENGLISH;
            double price = 100 + (i * 10);
            LocalDateTime createdAt = LocalDateTime.now().minusDays(i);
            LocalDateTime updatedAt = LocalDateTime.now();

            Course course = new Course(
                    title,
                    description,
                    startAt,
                    endAt,
                    slug,
                    new DifficultyLevel(),
                    new sa.mhmdfayedh.CourseConnect.entities.Language(),
                    price,
                    createdAt,
                    updatedAt
            );

            courses.add(course);
        }

        return courses;
    }
}