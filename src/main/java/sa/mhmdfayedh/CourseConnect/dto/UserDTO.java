package sa.mhmdfayedh.CourseConnect.dto;

import sa.mhmdfayedh.CourseConnect.enums.Gender;
import sa.mhmdfayedh.CourseConnect.enums.Role;
import sa.mhmdfayedh.CourseConnect.models.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserDTO {
    private int id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private String background;
    private Gender gender;
    private LocalDate dateOfBirth;
    private boolean isActive;
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
        this.role = user.getRole();
        this.background = user.getBackground();
        this.gender = user.getGender();
        this.dateOfBirth = user.getDateOfBirth();
        this.isActive = user.getIsActive();
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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
