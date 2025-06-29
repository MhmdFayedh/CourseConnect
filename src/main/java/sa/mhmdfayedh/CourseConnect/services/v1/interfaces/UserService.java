package sa.mhmdfayedh.CourseConnect.services.v1.interfaces;

import sa.mhmdfayedh.CourseConnect.dto.v1.*;

public interface UserService {
    ResponseDTO<UserDTO> registerUser(CreateUserRequestDTO userDTO);
    ResponseDTO<UserDTO> findAllUsers(int pageNumber, String sort);
    ResponseDTO<UserDTO> findUserById(int id);
    ResponseDTO<UserDTO> updateUser(UpdateUserRequestDTO  userRequestDTO);
    ResponseDTO<UserDTO> deleteUser(int id);
}
