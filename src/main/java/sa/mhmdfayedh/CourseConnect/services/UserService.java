package sa.mhmdfayedh.CourseConnect.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sa.mhmdfayedh.CourseConnect.common.PasswordUtil;
import sa.mhmdfayedh.CourseConnect.common.mappers.UserMapper;
import sa.mhmdfayedh.CourseConnect.dto.*;
import sa.mhmdfayedh.CourseConnect.enums.Gender;
import sa.mhmdfayedh.CourseConnect.enums.Role;
import sa.mhmdfayedh.CourseConnect.models.User;
import sa.mhmdfayedh.CourseConnect.repositories.UserDAO;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    UserDAO userDAO;


    @Autowired
    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }


    public RegisterUserResponseDTO registerUser(CreateUserRequestDTO userDTO){
        if (userDTO == null) {
            throw new IllegalArgumentException("User cannot be null");
        }



        // Validate Role & Gender if incorrect will throw an exception
        String role = Role.fromString(userDTO.getRole()).getDisplyName();
        String gender = Gender.fromString(userDTO.getGender()).getDisplayName();


        userDTO.setRole(role);
        userDTO.setGender(gender);
        String hashedPassword = PasswordUtil.hash(userDTO.getPassword());
        userDTO.setPassword("{bcrypt}" + hashedPassword);



        User mppedUser = UserMapper.toEntity(userDTO);

        User user = this.userDAO.save(mppedUser);

        /* Preparing the response and return to the controller */
        RegisterUserResponseDTO dto = new RegisterUserResponseDTO();



        dto.setStatus("success");
        dto.setStatusCode(HttpStatus.OK.value());
        dto.setMessage("User Created Successfully");
        dto.setData(UserMapper.toDTOList(List.of(user)));

        return dto;

    }

    public GetUsersResponseDTO findAllUsers(){
        List<User> users = userDAO.findAll();

        GetUsersResponseDTO dto = new GetUsersResponseDTO();
        dto.setStatus("success");
        dto.setStatusCode(HttpStatus.OK.value());
        dto.setMessage("Users Retrieved Successfully");
        dto.setData(UserMapper.toDTOList(users));
        dto.setTotal(users.size());

        return dto;
    }

    public GetUserResponseDTO findUserById(int id){
        User user = userDAO.findById(id);

        GetUserResponseDTO dto = new GetUserResponseDTO();

        dto.setStatus("success");
        dto.setStatusCode(HttpStatus.OK.value());
        dto.setMessage("User Retrieved Successfully");
        dto.setData(UserMapper.toDTOList(List.of(user)));

        return dto;
    }



    public UpdateUserResponseDTO updateUser(UpdateUserRequestDTO userRequestDTO){
        if (userRequestDTO == null){
            throw new IllegalArgumentException("User cannot be null");
        }


        User userEntity = UserMapper.toEntity(userRequestDTO);
        User user = userDAO.findById(userEntity.getId());
        user.setUsername(userEntity.getUsername());
        user.setEmail(userEntity.getEmail());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setRole(userEntity.getRole());
        user.setBackground(userEntity.getBackground());
        user.setGender(userEntity.getGender());
        user.setDateOfBirth(userEntity.getDateOfBirth());
        user.setUpdatedAt(LocalDateTime.now());

        this.userDAO.update(user);

        UpdateUserResponseDTO dto = new UpdateUserResponseDTO();


        dto.setStatus("success");
        dto.setStatusCode(HttpStatus.OK.value());
        dto.setMessage("User Updated Successfully");
        dto.setData(UserMapper.toDTOList(List.of(user)));

        return dto;
    }

    public DeleteUserResponseDTO deleteUser(int id){
        this.userDAO.deleteById(id);

        DeleteUserResponseDTO dto = new DeleteUserResponseDTO();
        dto.setStatus("success");
        dto.setStatusCode(HttpStatus.OK.value());
        dto.setMessage("User Deleted Successfully");

        return dto;
    }
}
