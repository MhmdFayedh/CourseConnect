package sa.mhmdfayedh.CourseConnect.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "difficulty_levels")
public class DifficultyLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @OneToMany(mappedBy = "difficultLevel")
    private List<Course> courses;

    public DifficultyLevel(){

    }

    public DifficultyLevel(String name, boolean isDeleted, List<Course> courses) {
        this.name = name;
        this.isDeleted = isDeleted;
        this.courses = courses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
