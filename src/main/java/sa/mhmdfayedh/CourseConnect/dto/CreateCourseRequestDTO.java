package sa.mhmdfayedh.CourseConnect.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.*;
import sa.mhmdfayedh.CourseConnect.models.User;

import java.time.LocalDateTime;
@JsonIgnoreProperties(ignoreUnknown = false)
public class CreateCourseRequestDTO {

    @NotBlank(message = "Title could not be empty")
    private String title;
    @NotBlank(message = "Description could not be empty")
    private String description;
    @NotNull(message = "Start date and time cannot be empty")
    @FutureOrPresent(message = "Start date and time must be in the future or present")
    private LocalDateTime startAt;
    @NotNull(message = "End date and time cannot be empty")
    @Future(message = "End date and time cannot be empty")
    private LocalDateTime endAt;
    @NotBlank(message = "slug cannot be empty")
    private String slug;
    @NotBlank(message = "Difficult level cannot be empty")
    private String difficultLevel;
    @NotBlank(message = "Language cannot be empty")
    private String language;
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.1", message = "Price must be at least 0.1")
    private Double price;
    @NotNull(message = "Instructor ID is required")
    User instructor;

    public CreateCourseRequestDTO(String title,
                                  String description,
                                  LocalDateTime startAt,
                                  LocalDateTime endAt,
                                  String slug,
                                  String difficultLevel,
                                  String language,
                                  double price,
                                  User instructor) {
        this.title = title;
        this.description = description;
        this.startAt = startAt;
        this.endAt = endAt;
        this.slug = slug;
        this.difficultLevel = difficultLevel;
        this.language = language;
        this.price = price;
        this.instructor = instructor;
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

    public String getDifficultLevel() {
        return difficultLevel;
    }

    public void setDifficultLevel(String difficultLevel) {
        this.difficultLevel = difficultLevel;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getInstructor() {
        return instructor;
    }

    public void setInstructor(User instructor) {
        this.instructor = instructor;
    }
}
