package sa.mhmdfayedh.CourseConnect.common.mappers;

import sa.mhmdfayedh.CourseConnect.dto.CreateCourseRequestDTO;
import sa.mhmdfayedh.CourseConnect.dto.CourseDTO;
import sa.mhmdfayedh.CourseConnect.dto.UpdateCourseRequestDTO;
import sa.mhmdfayedh.CourseConnect.enums.DifficultLevel;
import sa.mhmdfayedh.CourseConnect.enums.Language;
import sa.mhmdfayedh.CourseConnect.models.Course;

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
        dto.setDifficultLevel(course.getDifficultLevel());
        dto.setLanguage(course.getLanguage());
        dto.setPrice(course.getPrice());
        dto.setCreatedAt(course.getCreatedAt());
        dto.setUpdatedAt(course.getUpdatedAt());

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
        course.setDifficultLevel(DifficultLevel.valueOf(dto.getDifficultLevel()));
        course.setLanguage(Language.valueOf(dto.getLanguage()));
        course.setPrice(dto.getPrice());
        course.setInstructor(dto.getInstructor());
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
        course.setDifficultLevel(DifficultLevel.valueOf(dto.getDifficultLevel()));
        course.setLanguage(Language.valueOf(dto.getLanguage()));
        course.setPrice(dto.getPrice());
        course.setInstructor(dto.getInstructor());
        course.setUpdatedAt(LocalDateTime.now());

        return course;
    }


}
