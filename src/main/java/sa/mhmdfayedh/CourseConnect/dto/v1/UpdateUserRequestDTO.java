package sa.mhmdfayedh.CourseConnect.dto.v1;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class UpdateUserRequestDTO {
    @Min(value = 1, message = "User ID must be a positive number")
    private int id;
    @NotBlank(message = "Username cannot be empty")
    private String username;
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email not valid")
//    @Pattern("") TODO: Regex
    private String email;
    @NotBlank(message = "firstname cannot be empty")
    private String firstName;
    @NotBlank(message = "lastname cannot be empty")
    private String lastName;
    @NotNull(message = "Role cannot be empty")
    private int roleId;
    @NotBlank(message = "Background cannot be empty")
    private String background;
    @NotNull(message = "Gender cannot be empty")
    private int genderId;
    @NotNull(message = "Date of birth cannot be empty")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    public UpdateUserRequestDTO()
    {

    }
    public UpdateUserRequestDTO(int id,
                                String username,
                                String email,
                                String firstName,
                                String lastName,
                                int roleId,
                                String background,
                                int genderId,
                                LocalDate dateOfBirth) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleId = roleId;
        this.background = background;
        this.genderId = genderId;
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getGenderId() {
        return genderId;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
