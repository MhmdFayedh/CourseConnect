//package sa.mhmdfayedh.CourseConnect.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import sa.mhmdfayedh.CourseConnect.controllers.v1.impl.UserController;
//import sa.mhmdfayedh.CourseConnect.dto.v1.CreateUserRequestDTO;
//import sa.mhmdfayedh.CourseConnect.dto.v1.RegisterUserResponseDTO;
//import sa.mhmdfayedh.CourseConnect.dto.v1.UserDTO;
//import sa.mhmdfayedh.CourseConnect.services.v1.impl.UserServiceImpl;
//import sa.mhmdfayedh.CourseConnect.services.v1.interfaces.UserService;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//@WebMvcTest(UserController.class)
//class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    private UserService userService;
//
//
//    @Test
//    void shouldRegisterUserSuccessfully_WhenValidInputProvided() throws Exception {
//
//
//
//        CreateUserRequestDTO userRequest = new CreateUserRequestDTO(
//                "john_doe",
//                "john.doe@example.com",
//                "securePassword123",
//                "John",
//                "Doe",
//                1,
//                "Senior Java Developer with 10 years",
//                1,
//                LocalDate.of(1990, 4, 10)
//        );
//        UserDTO userDTO = new UserDTO();
//
//        userDTO.setId(1);
//        userDTO.setUsername("john_doe");
//        userDTO.setEmail("john.doe@example.com");
//        userDTO.setFirstName("John");
//        userDTO.setLastName("Doe");
//        userDTO.setRoleID(1);
//        userDTO.setBackground("Senior Java Developer with 10 years of experience");
//        userDTO.setGenderId(1);
//        userDTO.setDateOfBirth(LocalDate.of(1990, 4, 10));
//        userDTO.setDeleted(false);
//        userDTO.setCreatedAt(LocalDateTime.now().minusDays(30));
//        userDTO.setUpdatedAt(LocalDateTime.now());
//
//
//        RegisterUserResponseDTO responseDTO = new RegisterUserResponseDTO();
//
//        responseDTO.setStatusCode(201);
//        responseDTO.setStatus("success");
//        responseDTO.setMessage("User created successfully");
//        responseDTO.setData(List.of(userDTO));
//
//        when(userService.registerUser(any(CreateUserRequestDTO.class))).thenReturn(responseDTO);
//
//        mockMvc.perform(post("/api/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(userRequest)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.status").value("success"))
//                .andExpect(jsonPath("$.statusCode").value(201))
//                .andExpect(jsonPath("$.data[0].email").value("john.doe@example.com"));
//    }
//
////    @Test
////    void findAllUsers() {
////    }
////
////    @Test
////    void getUser() {
////    }
////
////    @Test
////    void updateUser() {
////    }
////
////    @Test
////    void deleteUser() {
////    }
//}