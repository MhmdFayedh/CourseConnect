package sa.mhmdfayedh.CourseConnect.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sa.mhmdfayedh.CourseConnect.dto.*;
import sa.mhmdfayedh.CourseConnect.services.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }


    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerUser(@RequestBody @Valid CreateUserRequestDTO user){
        RegisterUserResponseDTO responseDTO = userService.registerUser(user);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/users")
    public ResponseEntity<?> findAllUsers(){
         GetUsersResponseDTO responseDTO = this.userService.findAllUsers();

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable int id){
        GetUserResponseDTO responseDTO = userService.findUserById(id);

        return ResponseEntity.ok(responseDTO);
    }


    @PutMapping("/users")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequestDTO user){
        UpdateUserResponseDTO responseDTO = userService.updateUser(user);

        return ResponseEntity.ok(responseDTO);
    }

    // Delete a user
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id){
        DeleteUserResponseDTO responseDTO = this.userService.deleteUser(id);

        return ResponseEntity.ok(responseDTO);
    }

}
