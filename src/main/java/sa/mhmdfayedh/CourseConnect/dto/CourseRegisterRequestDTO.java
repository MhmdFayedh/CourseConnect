package sa.mhmdfayedh.CourseConnect.dto;

import jakarta.validation.constraints.Min;
import sa.mhmdfayedh.CourseConnect.models.Course;

public class CourseRegisterRequestDTO {
    @Min(value = 1, message = "Course ID must be a positive number")
    private int courseId;
    @Min(value = 1, message = "Course ID must be a positive number")
    private int studentId;

    public CourseRegisterRequestDTO(int courseId, int studentId) {

        this.courseId = courseId;
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
