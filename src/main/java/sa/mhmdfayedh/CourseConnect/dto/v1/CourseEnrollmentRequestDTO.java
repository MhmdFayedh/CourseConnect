package sa.mhmdfayedh.CourseConnect.dto.v1;

import jakarta.validation.constraints.Min;

public class CourseEnrollmentRequestDTO {
    @Min(value = 1, message = "Course ID must be a positive number")
    private int courseId;
    @Min(value = 1, message = "Course ID must be a positive number")
    private int studentId;

    public CourseEnrollmentRequestDTO(int courseId, int studentId) {

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
