package sa.mhmdfayedh.CourseConnect.dto.v1;

import java.util.List;

public class PostCourseResponseDTO {

    public PostCourseResponseDTO(){}


    private String status;
    private String message;
    private List<CourseDTO> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CourseDTO> getData() {
        return data;
    }

    public void setData(List<CourseDTO> data) {
        this.data = data;
    }
}
