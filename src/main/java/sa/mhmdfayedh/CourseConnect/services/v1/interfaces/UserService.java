package sa.mhmdfayedh.CourseConnect.services.v1.interfaces;

import sa.mhmdfayedh.CourseConnect.dto.v1.*;

public interface UserService {
    RegisterUserResponseDTO registerUser(CreateUserRequestDTO userDTO);
    GetUsersResponseDTO findAllUsers(int pageNumber, String sort);
    GetUserResponseDTO findUserById(int id);
    UpdateUserResponseDTO updateUser(UpdateUserRequestDTO  userRequestDTO);
    DeleteUserResponseDTO deleteUser(int id);
}
