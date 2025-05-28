package sa.mhmdfayedh.CourseConnect.dto;

import jakarta.validation.constraints.*;
import sa.mhmdfayedh.CourseConnect.enums.Gender;
import sa.mhmdfayedh.CourseConnect.enums.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UpdateUserRequestDTO {
    @Min(value = 1, message = "Course ID must be a positive number")
    private int id;
    @NotBlank(message = "Username cannot be empty")
    private String username;
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email not valid")
    private String email;
    @NotBlank(message = "firstname cannot be empty")
    private String firstName;
    @NotBlank(message = "lastname cannot be empty")
    private String lastName;
    @NotBlank(message = "Role cannot be empty")
    private Role role;
    @NotBlank(message = "Background cannot be empty")
    private String background;
    @NotBlank(message = "Gender cannot be empty")
    private Gender gender;
    @NotBlank(message = "Date of birth cannot be empty")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    public UpdateUserRequestDTO(int id, String username, String email, String firstName, String lastName, Role role, String background, Gender gender, LocalDate dateOfBirth) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.background = background;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
