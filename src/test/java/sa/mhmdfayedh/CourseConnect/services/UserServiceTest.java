package sa.mhmdfayedh.CourseConnect.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import sa.mhmdfayedh.CourseConnect.common.utils.AuthUtil;
import sa.mhmdfayedh.CourseConnect.common.utils.PasswordUtil;
import sa.mhmdfayedh.CourseConnect.dto.v1.*;
import sa.mhmdfayedh.CourseConnect.common.exceptions.UserNotFoundException;
import sa.mhmdfayedh.CourseConnect.entities.Course;
import sa.mhmdfayedh.CourseConnect.entities.Gender;
import sa.mhmdfayedh.CourseConnect.entities.Role;
import sa.mhmdfayedh.CourseConnect.entities.User;
import sa.mhmdfayedh.CourseConnect.repositories.interfaces.GenderDAO;
import sa.mhmdfayedh.CourseConnect.repositories.interfaces.RoleDAO;
import sa.mhmdfayedh.CourseConnect.repositories.interfaces.UserDAO;
import sa.mhmdfayedh.CourseConnect.security.JwtFilter;
import sa.mhmdfayedh.CourseConnect.services.v1.impl.GenderServiceImpl;
import sa.mhmdfayedh.CourseConnect.services.v1.impl.RoleServiceImpl;
import sa.mhmdfayedh.CourseConnect.services.v1.impl.UserServiceImpl;
import sa.mhmdfayedh.CourseConnect.services.v1.interfaces.GenderService;
import sa.mhmdfayedh.CourseConnect.services.v1.interfaces.RoleService;
import sa.mhmdfayedh.CourseConnect.services.v1.interfaces.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserDAO userDAO;
    @Mock
    private GenderDAO genderDAO;
    @Mock
    private RoleDAO roleDAO;
    @Mock
    private CacheManager cacheManager;
    @Mock
    private JwtFilter jwtFilter;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private AuthUtil authUtil;



    private UserService userService;
    private RoleService roleService;
    private GenderService genderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        genderService = new GenderServiceImpl(genderDAO) {
        };
        roleService = new RoleServiceImpl(roleDAO);
        userService = new UserServiceImpl(userDAO, roleService, genderService, cacheManager, jwtFilter, authenticationManager, authUtil);
    }





    /*
     * registerUser method (It retrieve users in the systems excluding deleted users)
     *
     * shouldReturnSuccessfulRegistrationResonance
     * shouldThrowIllegalArgumentException_whenCreateUserRequestIsNull
     * */

    @Test
    void shouldReturnSuccessfulRegistrationResonance(){
        // Arrange
        Role role = new Role();
        role.setName("ROLE_STUDENT");
        role.setDeleted(false);
        Gender gender = new Gender();
        gender.setName("MALE");
        gender.setDeleted(false);

        CreateUserRequestDTO requestDTO = new CreateUserRequestDTO();
        requestDTO.setUsername("yasser");
        requestDTO.setEmail("yasser@gmail.com");
        requestDTO.setPassword("User123@");
        requestDTO.setFirstName("Yasser");
        requestDTO.setLastName("AlShammari");
        requestDTO.setRole(1);
        requestDTO.setBackground("Yasser is bal bala blaa blaa");
        requestDTO.setGenderId(1);
        requestDTO.setDateOfBirth(LocalDate.of(1999, 10, 27));

        // Create a real User object with expected fields
        User savedUser = new User();
        savedUser.setId(1);
        savedUser.setUsername("yasser");
        savedUser.setEmail("yasser@gmail.com");
        savedUser.setFirstName("Yasser");
        savedUser.setLastName("AlShammari");
        savedUser.setRole(new Role());
        savedUser.setGender(new Gender());

        when(userDAO.save(any(User.class))).thenReturn(savedUser);
        when(genderService.findGenderById(requestDTO.getGenderId())).thenReturn(gender);
        when(roleService.findRoleById(requestDTO.getRoleId())).thenReturn(role);
        // Act
        ResponseDTO<UserDTO> response;
        try (MockedStatic<PasswordUtil> passwordUtilMock = mockStatic(PasswordUtil.class)) {
            passwordUtilMock.when(() -> PasswordUtil.hash("User123@")).thenReturn("encoded123");
            response = userService.registerUser(requestDTO);
        }

        // Assert
        UserDTO userDTO = response.getData().get(0);
        assertEquals("success", response.getStatus());
        assertEquals(201, response.getStatusCode()); // Or HttpStatus.CREATED.value()
        assertNotNull(response.getData());
        assertEquals("yasser", userDTO.getUsername());
        assertEquals("yasser@gmail.com", userDTO.getEmail());
        assertEquals("Yasser", userDTO.getFirstName());
        assertEquals("AlShammari", userDTO.getLastName());

        verify(userDAO).save(any(User.class));

    }

    @Test
    void shouldThrowIllegalArgumentException_whenCreateUserRequestIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(null);
        });


    }

    /*
    * findAllUsers method (It retrieve users in the systems excluding deleted users)
    *
    * shouldReturnUsersAndMetadata_whenUsersExist
    * shouldReturnEmptyList_whenNoUsersExist
    * shouldReturnValidResponseWithThousandUsers
    * */

    @Test
    void shouldReturnUsersAndMetadata_whenUsersExist() {
        // Arrange
        List<User> users = generateUsers(5, "ROLE_STUDENT");

        when(userDAO.findAll(1)).thenReturn(users);

        // Act
        ResponseDTO<UserDTO> responseDTO = userService.findAllUsers(1, "");

        // Assert

        assertEquals("success", responseDTO.getStatus());
        assertEquals(200, responseDTO.getStatusCode());
        assertEquals("Users retrieved successfully", responseDTO.getMessage());
        assertNotNull(responseDTO.getData());

    }

    @Test
    void shouldReturnEmptyList_whenNoUsersExist(){
        // Arrange

        when(userDAO.findAll()).thenReturn(Collections.emptyList());

        // Act
        ResponseDTO<UserDTO> responseDTO = userService.findAllUsers(1, "");

        // Assert
        assertEquals("success", responseDTO.getStatus());
        assertEquals(200, responseDTO.getStatusCode());
        assertEquals("Users not found", responseDTO.getMessage());

    }

    @Test
    void shouldReturnValidResponseWithThousandUsers(){
        // Arrange
        List<User> users = generateUsers(1000, "ROLE_STUDENT");

        when(userDAO.findAll(1)).thenReturn(users);

        // Act
        ResponseDTO<UserDTO> responseDTO = userService.findAllUsers(1, "");

        // Assert

        assertEquals("success", responseDTO.getStatus());
        assertEquals(200, responseDTO.getStatusCode());
        assertEquals("Users retrieved successfully", responseDTO.getMessage());
        assertNotNull(responseDTO.getData());
    }

    /*
     * findUserById method (It retrieve user based on the user id)
     *
     * shouldReturnGetUserResponseDTO_WithValidUser
     * shouldReturnThrowUserNotFound_withNoUserExist
     * */

    @Test
    void shouldReturnGetUserResponseDTO_WithValidUser() {
        // Arrange
        List<User> users = generateUsers(1, "ROLE_INSTRUCTOR");
        User user = users.get(0);
        user.setId(1);

        when(userDAO.findById(1)).thenReturn(user);


        // Act
        ResponseDTO<UserDTO> responseDTO = userService.findUserById(1);


        // Assert
        assertEquals("success", responseDTO.getStatus());
        assertEquals("User retrieved successfully", responseDTO.getMessage());
        assertEquals(200, responseDTO.getStatusCode());
        assertEquals(1, responseDTO.getData().get(0).getId());
        assertNotNull(responseDTO);

    }

    @Test
    void shouldReturnThrowUserNotFound_withNoUserExist(){
        when(userDAO.findById(1)).thenThrow(new UserNotFoundException("User not found"));

        // Act & Assert

        assertThrows(UserNotFoundException.class, () -> userService.findUserById(1));

    }



    /*
     * updateUser method (It update a user based on UpdateUserRequestDTO)
     *
     * shouldUpdateUserSuccessfully_whenValidRequestIsProvided
     * shouldThrowIllegalArgumentException_whenRequestDTOIsNull
     * */

    @Test
    void shouldUpdateUserSuccessfully_whenValidRequestIsProvided() {
        List<User> users = generateUsers(1, "ROLE_INSTRUCTOR");
        User user = users.get(0);
        user.setId(1);
        user.setEmail("test@example.com");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(user.getEmail());

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        when(userDAO.findByEmail(user.getEmail())).thenReturn(user);

        UpdateUserRequestDTO requestDTO = new UpdateUserRequestDTO();
        requestDTO.setId(1);
        requestDTO.setUsername("newuser");
        requestDTO.setEmail("new@example.com");
        requestDTO.setFirstName("New");
        requestDTO.setLastName("User");
        requestDTO.setRoleId(3);
        requestDTO.setBackground("Blue");
        requestDTO.setGenderId(1);
        requestDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));

        when(userDAO.findById(1)).thenReturn(user);

        ResponseDTO<UserDTO> responseDTO = userService.updateUser(requestDTO);


        assertEquals("success", responseDTO.getStatus());
        assertEquals(200, responseDTO.getStatusCode());
        assertEquals("User updated successfully", responseDTO.getMessage());
        assertEquals("newuser", responseDTO.getData().get(0).getUsername());

    }

    @Test
    void shouldThrowIllegalArgumentException_whenRequestDTOIsNull() {


        // arrange and act, assert
        assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(null);
        });

    }



    /*
     * deleteUser method (It delete a user based on id)
     *
     * shouldDeleteUserSuccessfully_whenValidIdProvided
     * shouldThrowUserNotFoundException_whenUserDoesNotExist
     * */

    @Test
    void shouldDeleteUserSuccessfully_whenValidIdProvided() {
        // Arrange
        int userId = 1;
        List<User> users = generateUsers(1, "ROLE_INSTRUCTOR");
        User user = users.get(0);
        user.setId(1);

        String email = "test@example.com";
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setEmail(email);
        mockUser.setUsername("testUser");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(email);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        when(userDAO.findByEmail(email)).thenReturn(mockUser);


        when(userDAO.findById(1)).thenReturn(user);

        // No need to mock deleteById if it's a void method (but verify it's called)
        when(userDAO.findById(1)).thenReturn(user);
        doNothing().when(userDAO).deleteById(userId);

        // Act
        ResponseDTO<UserDTO> response = userService.deleteUser(userId);

        // Assert
        assertNotNull(response);
        assertEquals("success", response.getStatus());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("User deleted successfully", response.getMessage());

        verify(userDAO, times(1)).deleteById(userId);
    }

    @Test
    void shouldThrowUserNotFoundException_whenUserDoesNotExist() {
        int invalidUserId = 999;

        doThrow(new UserNotFoundException("User not found"))
                .when(userDAO).deleteById(invalidUserId);

        assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser(invalidUserId);
        });

        verify(userDAO, never()).deleteById(anyInt());
    }


    private List<User> generateUsers(int count, String roleName ) {
        List<User> users = new ArrayList<>();
        List<Course> coursesByInstructor = Collections.emptyList();
        List<Course> courses = Collections.emptyList();
        Role role = new Role();
        role.setName(roleName);
        role.setDeleted(false);
        Gender gender = new Gender();
        gender.setName("MALE");
        gender.setDeleted(false);

        for (int i = 1; i <= count; i++) {
            users.add(new User(
                    "user" + i,
                    "user" + i + "@example.com",
                    "password" + i,
                    "FirstName" + i,
                    "LastName" + i,
                    "Background" + i,
                    LocalDate.of(2000, 1, 1).plusDays(i),
                    false,
                    LocalDateTime.now().minusDays(i),
                    LocalDateTime.now(),
                    coursesByInstructor,
                    courses,
                    role,
                    gender

            ));
        }
        return users;
    }




}