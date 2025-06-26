package sa.mhmdfayedh.CourseConnect.controllers.v1.intefaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sa.mhmdfayedh.CourseConnect.dto.v1.*;

public interface UserController {
    @Operation(
            summary = "Authenticate user and generate JWT token",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User authenticated successfully with JWT token",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LoginResponaceDTO.class)
                            )
                    ),
            }
    )
    ResponseEntity<?> loginUser(@RequestBody @Valid LoginRequestDTO loginRequestDTO);

    @Operation(
            summary = "Get paginated list of users",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved a paginated list of users",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetUsersResponseDTO.class)
                            )
                    )
            }
    )
    ResponseEntity<?> findAllUsers(
            @Parameter(description = "Page number for pagination", example = "1")
            @RequestParam(defaultValue = "1") int pageNumber,
            @Parameter(description = "Sort order of courses, e.g., 'title' or 'price'")
            @RequestParam(required = false) String sort);


    @Operation(
            summary = "Register a new user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully registered a new user and returned their details",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RegisterUserResponseDTO.class)
                            )
                    )
            }
    )
    ResponseEntity<?> registerUser(@RequestBody @Valid CreateUserRequestDTO user);

    @Operation(
            summary = "Get user by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved the user details by ID",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetUserResponseDTO.class)
                            )
                    )
            }
    )
    ResponseEntity<?> getUser(@Parameter(description = "User ID", example = "3")
                              @PathVariable int id);

    @Operation(
            summary = "Update user details",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully updated user information",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UpdateUserResponseDTO.class)
                            )
                    )
            }
    )
    ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequestDTO user);

    @Operation(
            summary = "Delete user by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully deleted the user",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UpdateUserResponseDTO.class)
                            )
                    )
            }
    )
    ResponseEntity<?> deleteUser(@Parameter(description = "User ID", example = "3")
                                 @PathVariable int id);
}
