package sa.mhmdfayedh.CourseConnect.dto.v1;

import sa.mhmdfayedh.CourseConnect.entities.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public UserDTO(){

    }


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

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getGenderId() {
        return genderId;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
