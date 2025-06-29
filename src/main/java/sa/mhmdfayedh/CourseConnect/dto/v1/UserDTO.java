package sa.mhmdfayedh.CourseConnect.dto.v1;

import lombok.Data;
import lombok.NoArgsConstructor;
import sa.mhmdfayedh.CourseConnect.entities.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserDTO implements Serializable {
    private int id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private int roleID;
    private String background;
    private int genderId;
    private LocalDate dateOfBirth;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;



    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.roleID = user.getRole().getId();
        this.background = user.getBackground();
        this.genderId = user.getGender().getId();
        this.dateOfBirth = user.getDateOfBirth();
        this.updatedAt = user.getUpdatedAt();
        this.createdAt = user.getCreatedAt();
    }

}
