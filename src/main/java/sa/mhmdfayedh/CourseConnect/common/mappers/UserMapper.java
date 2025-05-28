package sa.mhmdfayedh.CourseConnect.common.mappers;

import sa.mhmdfayedh.CourseConnect.dto.CreateUserRequestDTO;
import sa.mhmdfayedh.CourseConnect.dto.UpdateUserRequestDTO;
import sa.mhmdfayedh.CourseConnect.dto.UserDTO;

import sa.mhmdfayedh.CourseConnect.enums.Gender;
import sa.mhmdfayedh.CourseConnect.enums.Role;
import sa.mhmdfayedh.CourseConnect.models.User;

import java.time.LocalDateTime;
import java.util.List;

public class UserMapper {
    public static UserDTO toDTO(User user){
        if (user == null) return null;

        UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setRole(user.getRole());
        dto.setBackground(user.getBackground());
        dto.setGender(user.getGender());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setActive(user.getIsActive());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        return dto;
    }

    public static List<UserDTO> toDTOList(List<User> users) {
        return users.stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    public static User toEntity(CreateUserRequestDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setRole(Role.valueOf(dto.getRole()));
        user.setBackground(dto.getBackground());
        user.setGender(Gender.valueOf(dto.getGender()));
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setIsActive(true);
        user.setCreateAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        return user;
    }

    public static User toEntity(UpdateUserRequestDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setRole(dto.getRole());
        user.setBackground(dto.getBackground());
        user.setGender(dto.getGender());
        user.setDateOfBirth(dto.getDateOfBirth());

        return user;
    }


}
