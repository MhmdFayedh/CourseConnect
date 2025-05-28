package sa.mhmdfayedh.CourseConnect.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import sa.mhmdfayedh.CourseConnect.enums.DifficultLevel;
import sa.mhmdfayedh.CourseConnect.enums.Language;
import sa.mhmdfayedh.CourseConnect.models.Course;
import sa.mhmdfayedh.CourseConnect.models.User;

import java.time.LocalDateTime;
import java.util.List;

public class CourseDTO {
    private int id;
    private String title;
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String slug;
    private DifficultLevel difficultLevel;
    private Language language;
    private double price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
//    private User instructor;
//    private List<User> users;

    public CourseDTO(){}
    public CourseDTO(Course course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.startAt = course.getStartAt();
        this.endAt = course.getEndAt();
        this.slug = course.getSlug();
        this.difficultLevel = course.getDifficultLevel();
        this.language = course.getLanguage();
        this.price = course.getPrice();
        this.createdAt = course.getCreatedAt();
        this.updatedAt = course.getUpdatedAt();
//        this.instructor = course.getInstructor();

    }



//    public User getInstructor() {
//        return instructor;
//    }
//
//    public void setInstructor(User instructor) {
//        this.instructor = instructor;
//    }

//    public List<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(List<User> users) {
//        this.users = users;
//    }






    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public DifficultLevel getDifficultLevel() {
        return difficultLevel;
    }

    public void setDifficultLevel(DifficultLevel difficultLevel) {
        this.difficultLevel = difficultLevel;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

//

}
