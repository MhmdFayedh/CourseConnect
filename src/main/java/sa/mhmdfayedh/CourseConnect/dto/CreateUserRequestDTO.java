package sa.mhmdfayedh.CourseConnect.dto;

import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.*;


import java.time.LocalDate;

public class CreateUserRequestDTO {

    @NotBlank(message = "Username cannot be empty")
    private String username;
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email not valid")
    private String email;
    @NotBlank(message = "firstname cannot be empty")
    private String firstName;
    @NotBlank(message = "Password Cannot be empty")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
    private String password;
    @NotBlank(message = "lastname cannot be empty")
    private String lastName;
    @NotBlank(message = "Role cannot be empty")
    private String role;
    @NotBlank(message = "Background cannot be empty")
    private String background;
    @NotBlank(message = "Gender cannot be empty")
    private String gender;
    @NotNull(message = "Date of birth cannot be empty")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    public CreateUserRequestDTO(){

    }

    public CreateUserRequestDTO(String username,
                                String email,
                                String password,
                                String firstName,
                                String lastName,
                                String role,
                                String background,
                                String gender,
                                LocalDate dateOfBirth) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.background = background;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }



    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
