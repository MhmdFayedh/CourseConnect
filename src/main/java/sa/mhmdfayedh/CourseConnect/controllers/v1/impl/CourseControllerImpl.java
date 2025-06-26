package sa.mhmdfayedh.CourseConnect.controllers.v1.impl;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sa.mhmdfayedh.CourseConnect.controllers.v1.intefaces.CourseController;
import sa.mhmdfayedh.CourseConnect.dto.v1.*;
import sa.mhmdfayedh.CourseConnect.services.v1.impl.CourseServiceImpl;
import sa.mhmdfayedh.CourseConnect.services.v1.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/v1/courses")
@Tag(name = "Course Management", description = "Operations related to Courses")
public class CourseControllerImpl implements CourseController {
    CourseServiceImpl courseService;
    UserServiceImpl userService;

    @Autowired
    public CourseControllerImpl(CourseServiceImpl courseService, UserServiceImpl userService){
        this.courseService = courseService;
        this.userService = userService;
    }

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<?> createCourse(@RequestBody @Valid CreateCourseRequestDTO course){
        CreateCourseResponseDTO responseDTO =  courseService.createCourse(course);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<?> findAllCourses (
            @Parameter(description = "Page number for pagination", example = "1")
            @RequestParam(defaultValue = "1") int pageNumber,
            @Parameter(description = "Sort order of courses, e.g., 'title' or 'price'")
            @RequestParam(required = false) String sort){

        GetCoursesResponseDTO responseDTO = courseService.findAllCourses(pageNumber, sort);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<?> getCourse(@Parameter(description = "Course ID", example = "3")
                                           @PathVariable int id){
        GetCourseResponseDTO responseDTO = courseService.findCourseById(id);

        return  ResponseEntity.ok(responseDTO);
    }

    @PutMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<?> updateCourse(@RequestBody @Valid UpdateCourseRequestDTO course) {
        UpdateCourseResponseDTO responseDTO =  courseService.updateCourse(course);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<?> deleteCourse(@Parameter(description = "Course ID", example = "3")
                                              @PathVariable int id) {
        DeleteCourseResponseDTO responseDTO = this.courseService.deleteCourse(id);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/enroll")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> courseEnroll(@RequestBody @Valid CourseEnrollmentRequestDTO requestDTO) {
        CourseEnrollmentResponseDTO responseDTO = courseService.courseEnrollment(requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}/students")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<?> getCouresAndStudents(@Parameter(description = "Course ID", example = "3")
                                                      @PathVariable int id,
                                                  @Parameter(description = "Page number for pagination", example = "1")
                                                  @RequestParam(defaultValue = "1") int pageNumber) {
        GetUsersResponseDTO responseDTO = courseService.getCourseAndStudents(id, pageNumber);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<?> getCourseByTitle(
            @Parameter(description = "Page number for pagination", example = "1")
            @RequestParam(defaultValue = "1") int pageNumber,
            @Parameter(description = "Title of the course or part of it", example = "spring")
            @RequestParam(required = false) String title,
            @Parameter(description = "Price of the course where it's equals or less", example = "14.99")
            @RequestParam(required = false) Double price){
        GetCoursesResponseDTO responseDTO = this.courseService.getCourseByTitleAndPrice(pageNumber, title, price);

        return ResponseEntity.ok(responseDTO);
    }

}
