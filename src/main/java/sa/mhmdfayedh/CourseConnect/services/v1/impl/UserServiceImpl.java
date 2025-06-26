package sa.mhmdfayedh.CourseConnect.services.v1.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import sa.mhmdfayedh.CourseConnect.common.PasswordUtil;
import sa.mhmdfayedh.CourseConnect.common.enums.RoleEnum;
import sa.mhmdfayedh.CourseConnect.common.mappers.UserMapper;
import sa.mhmdfayedh.CourseConnect.dto.v1.*;
import sa.mhmdfayedh.CourseConnect.common.exceptions.UserNotFoundException;
import sa.mhmdfayedh.CourseConnect.entities.Gender;
import sa.mhmdfayedh.CourseConnect.entities.Role;
import sa.mhmdfayedh.CourseConnect.entities.User;
import sa.mhmdfayedh.CourseConnect.repositories.interfaces.UserDAO;
import sa.mhmdfayedh.CourseConnect.security.JwtFilter;
import sa.mhmdfayedh.CourseConnect.services.v1.interfaces.GenderService;
import sa.mhmdfayedh.CourseConnect.services.v1.interfaces.RoleService;
import sa.mhmdfayedh.CourseConnect.services.v1.interfaces.UserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final RoleService roleService;
    private final GenderService genderService;
    private final CacheManager cacheManager;
    private final JwtFilter jwtFilter;
    private final AuthenticationManager authenticationManager;



    @Autowired
    public UserServiceImpl(UserDAO userDAO, RoleService roleService, GenderService genderService, CacheManager cacheManager,  JwtFilter jwtFilter, AuthenticationManager authenticationManager){
        this.userDAO = userDAO;
        this.roleService = roleService;
        this.genderService = genderService;
        this.cacheManager = cacheManager;
        this.jwtFilter = jwtFilter;
        this.authenticationManager = authenticationManager;
    }


    public RegisterUserResponseDTO registerUser(CreateUserRequestDTO userDTO){
        if (userDTO == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        Role role = roleService.findRoleById(userDTO.getRoleId());
        Gender gender = genderService.findGenderById(userDTO.getGenderId());

        if (role.getName().equals(RoleEnum.ROLE_ADMIN.getDisplyName())) {
            throw new RuntimeException("Cannot register as admin");
        }

        String hashedPassword = PasswordUtil.hash(userDTO.getPassword());
        userDTO.setPassword("{bcrypt}" + hashedPassword);

        User mppedUser = UserMapper.toEntity(userDTO);


        mppedUser.setRole(role);
        mppedUser.setGender(gender);


        User user = this.userDAO.save(mppedUser);

        RegisterUserResponseDTO dto = new RegisterUserResponseDTO();



        dto.setStatus("success");
        dto.setStatusCode(HttpStatus.CREATED.value());
        dto.setMessage("User Created Successfully");
        dto.setData(UserMapper.toDTOList(List.of(user)));

        return dto;
    }

    public LoginResponaceDTO loginUser(LoginRequestDTO requestDTO) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword()
                )
        );

        User user = userDAO.findByEmail(requestDTO.getEmail());
        Role role = roleService.findRoleById(user.getRole().getId());

        String token = jwtFilter.generateToken(user.getEmail(), role.getName());

        return new LoginResponaceDTO(requestDTO.getEmail(), token);

    }

    public GetUsersResponseDTO findAllUsers(int pageNumber, String sort){
        List<User> users;
        Long UsersCount = userDAO.countUsers();


        if (sort != null && !sort.isBlank()) {
            users = userDAO.findAll(pageNumber, sort);
        } else {
            users = userDAO.findAll(pageNumber);
        }




        if (users == null){
            users = Collections.emptyList();
        }

        String message = users.isEmpty() ? "Users not found" : "Users retrieved successfully";


        GetUsersResponseDTO dto = new GetUsersResponseDTO();
        PaginationDTO pagination = new PaginationDTO();

        pagination.setSize(users.size());
        pagination.setTotalElements(UsersCount);
        pagination.setTotalPages((UsersCount + 30 - 1) / 30);
        pagination.setPage(pageNumber);
        pagination.setFirst(pageNumber == 1);
        pagination.setLast(pageNumber == (UsersCount + 30 - 1) / 30 );

        dto.setStatus("success");
        dto.setStatusCode(HttpStatus.OK.value());
        dto.setMessage(message);
        dto.setPagination(pagination);
        dto.setData(UserMapper.toDTOList(users));


        return dto;
    }

    public GetUserResponseDTO findUserById(int id){
        Cache userCache = cacheManager.getCache("userCache");
        UserDTO userDTO = userCache != null ? userCache.get(id, UserDTO.class) : null;

        if (userDTO == null) {
            User user = userDAO.findById(id);
            userDTO = UserMapper.toDTO(user);

            if (userCache != null) {
                userCache.put(id, userDTO);
            }
        }

        GetUserResponseDTO dto = new GetUserResponseDTO();

        dto.setStatus("success");
        dto.setStatusCode(HttpStatus.OK.value());
        dto.setMessage("User retrieved successfully");
        dto.setData(List.of(userDTO));

        return dto;
    }


    public UpdateUserResponseDTO updateUser(UpdateUserRequestDTO  userRequestDTO){
        if (userRequestDTO == null){
            throw new IllegalArgumentException("User cannot be null");
        }


        Role role = roleService.findRoleById(userRequestDTO.getRoleId());
        Gender gender = genderService.findGenderById(userRequestDTO.getGenderId());

        User userEntity = UserMapper.toEntity(userRequestDTO);
        User user = userDAO.findById(userRequestDTO.getId());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser  = userDAO.findByEmail(authentication.getName());


        if (user.getId() != currentUser.getId()
                && !currentUser.getRole().getName().equals(RoleEnum.ROLE_ADMIN.getDisplyName())) {
            throw new RuntimeException("You are not authorized to update another User");
        }

        user.setUsername(userEntity.getUsername());
        user.setEmail(userEntity.getEmail());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setRole(role);
        user.setBackground(userEntity.getBackground());
        user.setGender(gender);
        user.setDateOfBirth(userEntity.getDateOfBirth());
        user.setUpdatedAt(LocalDateTime.now());

        this.userDAO.update(user);

        Cache cache = cacheManager.getCache("userCache");
        if (cache != null){
            cache.put(user.getId(), user);
        }


        UpdateUserResponseDTO dto = new UpdateUserResponseDTO();


        dto.setStatus("success");
        dto.setStatusCode(HttpStatus.OK.value());
        dto.setMessage("User updated successfully");
        dto.setData(UserMapper.toDTOList(List.of(user)));

        return dto;
    }

    public DeleteUserResponseDTO deleteUser(int id)  {
        User user = userDAO.findById(id);
        if (user == null){
            throw new UserNotFoundException("Cannot delete user due to user not found");
        }


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser  = userDAO.findByEmail(authentication.getName());


        if (user.getId() != currentUser.getId()
                && !currentUser.getRole().getName().equals(RoleEnum.ROLE_ADMIN.getDisplyName())) {
            throw new RuntimeException("You are not authorized to delete another User");
        }

        this.userDAO.deleteById(id);

        Cache cache = cacheManager.getCache("userCache");
        if (cache != null) {
            cache.evict(id);
        }

        DeleteUserResponseDTO dto = new DeleteUserResponseDTO();
        dto.setStatus("success");
        dto.setStatusCode(HttpStatus.OK.value());
        dto.setMessage("User deleted successfully");

        return dto;
    }
}
