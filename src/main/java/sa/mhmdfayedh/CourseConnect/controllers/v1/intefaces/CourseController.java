package sa.mhmdfayedh.CourseConnect.controllers.v1.intefaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import sa.mhmdfayedh.CourseConnect.dto.v1.*;

public interface CourseController {

    @Operation(
            summary = "Create a new course",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Course created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CreateCourseResponseDTO.class)
                            )
                    )
            }
    )
    ResponseEntity<?> createCourse(@RequestBody @Valid CreateCourseRequestDTO course);

    @Operation(
            summary = "Get paginated list of courses",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of courses returned successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetCoursesResponseDTO.class)
                            )
                    )
            }
    )
    ResponseEntity<?> findAllCourses (
            @Parameter(description = "Page number for pagination", example = "1")
            @RequestParam(defaultValue = "1") int pageNumber,
            @Parameter(description = "Sort order of courses, e.g., 'title' or 'price'")
            @RequestParam(required = false) String sort);


    @Operation(
            summary = "Get course by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Course details returned successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetCourseResponseDTO.class)
                            )
                    )
            }
    )
    ResponseEntity<?> getCourse(@Parameter(description = "Course ID", example = "3")
                                @PathVariable int id);
    @Operation(
            summary = "Update course details",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Course updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UpdateCourseResponseDTO.class)
                            )
                    )
            }
    )
    ResponseEntity<?> updateCourse(@RequestBody @Valid UpdateCourseRequestDTO course);


    @Operation(
            summary = "Delete a course by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Course deleted successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DeleteCourseResponseDTO.class)
                            )
                    )
            }
    )
    ResponseEntity<?> deleteCourse(@Parameter(description = "Course ID", example = "3")
                                   @PathVariable int id);

    @Operation(
            summary = "Enroll a student to a course",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User registered to course successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CourseEnrollmentResponseDTO.class)
                            )
                    )
            }
    )
    ResponseEntity<?> courseEnroll(@RequestBody @Valid CourseEnrollmentRequestDTO requestDTO);

    @Operation(
            summary = "Get list of enrolled students in a course",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of enrolled students returned successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetUsersResponseDTO.class)
                            )
                    )
            }
    )
    ResponseEntity<?> getCouresAndStudents(@Parameter(description = "Course ID", example = "3")
                                           @PathVariable int id,
                                           @Parameter(description = "Page number for pagination", example = "1")
                                           @RequestParam(defaultValue = "1") int pageNumber);

    @Operation(
            summary = "Search courses by title, price, or both",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Filtered list of courses returned successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetCoursesResponseDTO.class)
                            )
                    )
            }
    )
    ResponseEntity<?> getCourseByTitle(
            @Parameter(description = "Page number for pagination", example = "1")
            @RequestParam(defaultValue = "1") int pageNumber,
            @Parameter(description = "Title of the course or part of it", example = "spring")
            @RequestParam(required = false) String title,
            @Parameter(description = "Price of the course where it's equals or less", example = "14.99")
            @RequestParam(required = false) Double price);
}
