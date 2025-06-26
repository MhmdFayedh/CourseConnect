package sa.mhmdfayedh.CourseConnect.dto.v1;

import sa.mhmdfayedh.CourseConnect.entities.Course;

import java.time.LocalDateTime;

public class CourseDTO {
    private int id;
    private String title;
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String slug;
    private int difficultLevelId;
    private int languageId;
    private double price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int instructorId;
//    private List<User> users;

    public CourseDTO(){}

    public CourseDTO(Course course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.startAt = course.getStartAt();
        this.endAt = course.getEndAt();
        this.slug = course.getSlug();
        this.difficultLevelId = course.getDifficultLevel().getId();
        this.languageId = course.getLanguage().getId();
        this.price = course.getPrice();
        this.createdAt = course.getCreatedAt();
        this.updatedAt = course.getUpdatedAt();
        this.instructorId = course.getInstructor().getId();

    }

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

    public int getDifficultLevelId() {
        return difficultLevelId;
    }

    public void setDifficultLevelId(int difficultLevelId) {
        this.difficultLevelId = difficultLevelId;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
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

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }
}
