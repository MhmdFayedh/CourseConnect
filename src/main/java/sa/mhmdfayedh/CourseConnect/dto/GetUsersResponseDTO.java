package sa.mhmdfayedh.CourseConnect.dto;

import java.util.List;

public class GetUsersResponseDTO {
    private String status;
    private int statusCode;
    private String message;
    private List<UserDTO> data;
    private int total;

    public GetUsersResponseDTO(){

    }

    public GetUsersResponseDTO(String status, int statusCode, String message, List<UserDTO> data, int total) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
