package sa.mhmdfayedh.CourseConnect.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import sa.mhmdfayedh.CourseConnect.enums.Gender;
import sa.mhmdfayedh.CourseConnect.enums.Role;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @Column(name = "background")
    private String background;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender" )
    private Gender gender;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @JsonIgnore
    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Course> coursesByInstructor;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "users")
    private List<Course> courses;


    public User(){}

    public User(String username, String email, String password, String firstName, String lastName, Role role, String background, Gender gender, LocalDate dateOfBirth, boolean isActive, LocalDateTime createAt, LocalDateTime updatedAt) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.background = background;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.isActive = isActive;
        this.createdAt = createAt;
        this.updatedAt = updatedAt;
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

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createdAt = createAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Course> getCoursesByInstructor() {
        return coursesByInstructor;
    }

    public void setCoursesByInstructor(List<Course> coursesByInstructor) {
        this.coursesByInstructor = coursesByInstructor;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course){
        if (this.courses == null){
            this.courses = new ArrayList<>();
        }

        this.courses.add(course);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                ", background='" + background + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", isActive=" + isActive +
                ", createAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", coursesByInstructor=" + coursesByInstructor +
                ", courses=" + courses +
                '}';
    }
}
