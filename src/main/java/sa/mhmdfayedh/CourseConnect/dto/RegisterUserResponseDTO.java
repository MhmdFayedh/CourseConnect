package sa.mhmdfayedh.CourseConnect.dto;

import java.util.List;

public class RegisterUserResponseDTO {
    private String status;
    private int statusCode;
    private String message;
    private List<UserDTO> data;

    public RegisterUserResponseDTO(){

    }

    public RegisterUserResponseDTO(String status,int statusCode, String message, List<UserDTO> data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.statusCode = statusCode;
    }

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

    public List<UserDTO> getData() {
        return data;
    }

    public void setData(List<UserDTO> data) {
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
