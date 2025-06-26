package sa.mhmdfayedh.CourseConnect.common.mappers;

import sa.mhmdfayedh.CourseConnect.dto.v1.CreateUserRequestDTO;
import sa.mhmdfayedh.CourseConnect.dto.v1.UpdateUserRequestDTO;
import sa.mhmdfayedh.CourseConnect.dto.v1.UserDTO;


import sa.mhmdfayedh.CourseConnect.entities.User;

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
        dto.setBackground(user.getBackground());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        if (user.getRole() != null)
            dto.setRoleID(user.getRole().getId());

        if (user.getGender() != null)
            dto.setGenderId(user.getGender().getId());


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
        user.setBackground(dto.getBackground());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setCreatedAt(LocalDateTime.now());
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
        user.setBackground(dto.getBackground());
        user.setDateOfBirth(dto.getDateOfBirth());

        return user;
    }


}
