package sa.mhmdfayedh.CourseConnect.controllers.v1.impl;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import sa.mhmdfayedh.CourseConnect.controllers.v1.intefaces.UserController;
import sa.mhmdfayedh.CourseConnect.dto.v1.*;
import sa.mhmdfayedh.CourseConnect.security.JwtFilter;
import sa.mhmdfayedh.CourseConnect.services.v1.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Management", description = "Operations related to Users")
public class UserControllerImpl implements UserController {
    private final UserServiceImpl userService;
    private final JwtFilter jwtFilter;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserControllerImpl(UserServiceImpl userService, JwtFilter jwtFilter, AuthenticationManager authenticationManager){
        this.userService = userService;
        this.jwtFilter = jwtFilter;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginRequestDTO loginRequestDTO){
        LoginResponaceDTO responseDTO = this.userService.loginUser(loginRequestDTO);

        return ResponseEntity.ok(responseDTO);

    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findAllUsers(
            @Parameter(description = "Page number for pagination", example = "1")
            @RequestParam(defaultValue = "1") int pageNumber,
            @Parameter(description = "Sort order of courses, e.g., 'title' or 'price'")
            @RequestParam(required = false) String sort){
        GetUsersResponseDTO responseDTO = this.userService.findAllUsers(pageNumber, sort);

        return ResponseEntity.ok(responseDTO);

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid CreateUserRequestDTO user){
        RegisterUserResponseDTO responseDTO = userService.registerUser(user);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<?> getUser(@Parameter(description = "User ID", example = "3")
                                         @PathVariable int id){
        GetUserResponseDTO responseDTO = userService.findUserById(id);

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequestDTO user){
        UpdateUserResponseDTO responseDTO = userService.updateUser(user);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<?> deleteUser(@Parameter(description = "User ID", example = "3")
                                            @PathVariable int id){
        DeleteUserResponseDTO responseDTO = this.userService.deleteUser(id);

        return ResponseEntity.ok(responseDTO);
    }

}
