package sa.mhmdfayedh.CourseConnect.dto.v1;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class UpdateCourseRequestDTO {
    @Min(value = 1, message = "Course ID must be a positive number")
    private int id;
    @NotBlank(message = "Title cannot be empty")
    private String title;
    @NotBlank(message = "Description cannot be empty")
    private String description;
    @NotNull(message = "Start date and time cannot be empty")
    private LocalDateTime startAt;
    @NotNull(message = "End date and time cannot be empty")
    private LocalDateTime endAt;
    @NotBlank(message = "slug cannot be empty")
    private String slug;
    @Min(value = 1, message = "Difficult Level ID must be a positive number")
    private int difficultLevelId;
    @Min(value = 1, message = "Language ID must be a positive number")
    private int languageId;
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.1", message = "Price must be at least 0.1")
    private double price;
    @Min(value = 1, message = "Instructor ID must be a positive number")
    private int instructorId;

    public UpdateCourseRequestDTO(){

    }

    public UpdateCourseRequestDTO(int id,
                                  String title,
                                  String description,
                                  LocalDateTime startAt,
                                  LocalDateTime endAt,
                                  String slug,
                                  int difficultLevelId,
                                  int languageId,
                                  double price,
                                  int instructorId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startAt = startAt;
        this.endAt = endAt;
        this.slug = slug;
        this.difficultLevelId = difficultLevelId;
        this.languageId = languageId;
        this.price = price;
        this.instructorId = instructorId;
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

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructor) {
        this.instructorId = instructor;
    }
}
