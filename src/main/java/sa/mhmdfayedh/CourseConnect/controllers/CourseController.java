package sa.mhmdfayedh.CourseConnect.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sa.mhmdfayedh.CourseConnect.dto.*;
import sa.mhmdfayedh.CourseConnect.services.CourseService;
import sa.mhmdfayedh.CourseConnect.services.UserService;


@RestController
@RequestMapping("/api")
public class CourseController {
    CourseService courseService;
    UserService userService;

    @Autowired
    public CourseController(CourseService courseService, UserService userService){
        this.courseService = courseService;
        this.userService = userService;
    }


    @PostMapping("/courses")
    public ResponseEntity<?> createCourse(@RequestBody @Valid CreateCourseRequestDTO course){
        CreateCourseResponseDTO responseDTO =  courseService.createCourse(course);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/courses")
    public ResponseEntity<?> findAllCourses(){

        GetCoursesResponseDTO responseDTO = courseService.findAllCourses();

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<?> getCourse(@PathVariable int id){
        GetCourseResponseDTO responseDTO = courseService.findCourseById(id);

        return  ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/courses")
    public ResponseEntity<?> updateCourse(@RequestBody @Valid UpdateCourseRequestDTO course ){
        UpdateCourseResponseDTO responseDTO =  courseService.updateCourse(course);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable int id){
        DeleteCourseResponseDTO responseDTO = this.courseService.deleteCourse(id);

        return ResponseEntity.ok(responseDTO);
    }


    @PostMapping("/courses/register")
    public ResponseEntity<?> courseRegister(@RequestBody @Valid CourseRegisterRequestDTO requestDTO ){
        RegisterCourseResponseDTO responseDTO = courseService.courseRegister(requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/courses/{id}/students")
    public ResponseEntity<?> getCouresAndStudents(@PathVariable int id){
        GetUsersResponseDTO responseDTO = courseService.getCourseAndStudents(id);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/courses/search")
    public ResponseEntity<?> getCourseByTitle(@RequestParam(required = false) String title, @RequestParam(required = false) Double price){

        // Check if
        GetCoursesResponseDTO responseDTO = this.courseService.getCourseByTitleAndPrice(title, price);

        return ResponseEntity.ok(responseDTO);
    }

}
