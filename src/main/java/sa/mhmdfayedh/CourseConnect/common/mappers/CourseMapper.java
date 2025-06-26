package sa.mhmdfayedh.CourseConnect.common.mappers;

import sa.mhmdfayedh.CourseConnect.dto.v1.CreateCourseRequestDTO;
import sa.mhmdfayedh.CourseConnect.dto.v1.CourseDTO;
import sa.mhmdfayedh.CourseConnect.dto.v1.UpdateCourseRequestDTO;
import sa.mhmdfayedh.CourseConnect.entities.Course;

import java.time.LocalDateTime;
import java.util.List;

public class CourseMapper {

    public static CourseDTO toDTO(Course course){
        if (course == null) return null;

        CourseDTO dto = new CourseDTO();

        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setStartAt(course.getStartAt());
        dto.setEndAt(course.getEndAt());
        dto.setSlug(course.getSlug());
        dto.setPrice(course.getPrice());
        dto.setCreatedAt(course.getCreatedAt());
        dto.setUpdatedAt(course.getUpdatedAt());

        if (course.getInstructor() != null && course.getInstructor().getId() != 0) {
            dto.setInstructorId(course.getInstructor().getId());
        }

        if (course.getLanguage().getId() != 0) {
            dto.setLanguageId(course.getLanguage().getId());
        }

        if (course.getDifficultLevel().getId() != 0){
            dto.setDifficultLevelId(course.getDifficultLevel().getId());
        }


        return dto;
    }

    public static List<CourseDTO> toDTOList(List<Course> courses){
        return courses.stream()
                .map(CourseMapper::toDTO)
                .toList();
    }


    public static Course toEntity(CreateCourseRequestDTO dto) {
        if (dto == null) return null;

        Course course = new Course();

        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setStartAt(dto.getStartAt());
        course.setEndAt(dto.getEndAt());
        course.setSlug(dto.getSlug());
        course.setPrice(dto.getPrice());
        course.setCreatedAt(LocalDateTime.now());
        course.setUpdatedAt(LocalDateTime.now());

        return course;
    }

    public static Course toEntity(UpdateCourseRequestDTO dto){
        if (dto == null) return null;

        Course course = new Course();
        course.setId(dto.getId());
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setStartAt(dto.getStartAt());
        course.setEndAt(dto.getEndAt());
        course.setSlug(dto.getSlug());
        course.setPrice(dto.getPrice());

        course.setUpdatedAt(LocalDateTime.now());

        return course;
    }


}
