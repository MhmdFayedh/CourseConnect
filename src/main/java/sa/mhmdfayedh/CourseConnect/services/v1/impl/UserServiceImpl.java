package sa.mhmdfayedh.CourseConnect.services.v1.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sa.mhmdfayedh.CourseConnect.common.exceptions.*;
import sa.mhmdfayedh.CourseConnect.common.utils.AuthUtil;
import sa.mhmdfayedh.CourseConnect.common.utils.PaginationUtils;
import sa.mhmdfayedh.CourseConnect.common.utils.PasswordUtil;
import sa.mhmdfayedh.CourseConnect.common.enums.RoleEnum;
import sa.mhmdfayedh.CourseConnect.common.mappers.UserMapper;
import sa.mhmdfayedh.CourseConnect.dto.v1.*;
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
    private final AuthUtil authUtil;



    @Autowired
    public UserServiceImpl(UserDAO userDAO,
                           RoleService roleService,
                           GenderService genderService,
                           CacheManager cacheManager,
                           JwtFilter jwtFilter,
                           AuthenticationManager authenticationManager,
                           AuthUtil authUtil){
        this.userDAO = userDAO;
        this.roleService = roleService;
        this.genderService = genderService;
        this.cacheManager = cacheManager;
        this.jwtFilter = jwtFilter;
        this.authenticationManager = authenticationManager;
        this.authUtil = authUtil;
    }


    public ResponseDTO<UserDTO> registerUser(CreateUserRequestDTO request){
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        if (userDAO.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email address is not available");
        }

        Role role = roleService.findRoleById(request.getRoleId());
        Gender gender = genderService.findGenderById(request.getGenderId());

        if (role == null ) {
            throw new RoleNotFoundException("Role not found with ID: " + request.getRoleId());
        }

        if (gender == null) {
            throw new GenderNotFoundException("Gender not found with ID: " + request.getGenderId());
        }


        if (role.getName().equals(RoleEnum.ROLE_ADMIN.getDisplyName())) {
            throw new CannotRegisterAsAdminException("Cannot register user with admin role");
        }

        String hashedPassword = PasswordUtil.hash(request.getPassword());
        request.setPassword("{bcrypt}" + hashedPassword);

        User mappedUser  = UserMapper.toEntity(request);

        mappedUser.setRole(role);
        mappedUser.setGender(gender);

        User user = this.userDAO.save(mappedUser);

        return ResponseDTO
                .<UserDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("User Created Successfully")
                .data(UserMapper.toDTOList(List.of(user)))
                .build();
    }

    public LoginResponaceDTO loginUser(LoginRequestDTO request) {
        if (request == null) {
            throw new IllegalArgumentException("Login request cannot be null");
        }

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()
                    )
            );


            String role = auth.getAuthorities()
                    .stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse(null);

            if (role == null) {
                throw new InvalidCredentialsException("Invalid email or password");
            }

            String token = jwtFilter.generateToken(auth.getName(), role);

            return new LoginResponaceDTO(request.getEmail(), token);

        } catch (UsernameNotFoundException
                 | BadCredentialsException
                 | DisabledException
                 | UserNotFoundException e) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
    }

    public ResponseDTO<UserDTO> findAllUsers(int pageNumber, String sort){
        List<User> users;
        int usersCount = userDAO.countUsers();

        if (sort != null && !sort.isBlank()) {
            users = userDAO.findAll(pageNumber, sort);
        } else {
            users = userDAO.findAll(pageNumber);
        }

        if (users == null){
            users = Collections.emptyList();
        }

        String message = users.isEmpty() ? "Users not found" : "Users retrieved successfully";

        PaginationDTO pagination = PaginationUtils
                .createPagination(users,
                        usersCount,
                        pageNumber,
                        30);

        return ResponseDTO
                .<UserDTO>builder()
                .message(message)
                .data(UserMapper.toDTOList(users)).
                pagination(pagination)
                .build();
    }

    public ResponseDTO<UserDTO> findUserById(int id){
        Cache userCache = cacheManager.getCache("userCache");
        UserDTO userDTO = userCache != null ? userCache.get(id, UserDTO.class) : null;

        if (userDTO == null) {
            User user = userDAO.findById(id);
            userDTO = UserMapper.toDTO(user);

            if (userCache != null) {
                userCache.put(id, userDTO);
            }
        }

        return ResponseDTO
                .<UserDTO>builder()
                .message("User retrieved successfully")
                .data(List.of(userDTO))
                .build();
    }


    public ResponseDTO<UserDTO> updateUser(UpdateUserRequestDTO  userRequestDTO){
        if (userRequestDTO == null){
            throw new IllegalArgumentException("User cannot be null");
        }


        Role role = roleService.findRoleById(userRequestDTO.getRoleId());
        Gender gender = genderService.findGenderById(userRequestDTO.getGenderId());

        User user = userDAO.findById(userRequestDTO.getId());


        authUtil.requireSelfOrAdmin(user.getId(), "You are not authorized to update another User");


        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setRole(role);
        user.setBackground(userRequestDTO.getBackground());
        user.setGender(gender);
        user.setDateOfBirth(userRequestDTO.getDateOfBirth());
        user.setUpdatedAt(LocalDateTime.now());

        this.userDAO.update(user);

        Cache cache = cacheManager.getCache("userCache");
        if (cache != null){
            cache.put(user.getId(), UserMapper.toDTO(user));
        }

        return ResponseDTO
                .<UserDTO>builder()
                .message("User updated successfully")
                .data(UserMapper.toDTOList(List.of(user)))
                .build();
    }

    public ResponseDTO<UserDTO> deleteUser(int id)  {
        User user = userDAO.findById(id);
        if (user == null){
            throw new UserNotFoundException("Cannot delete user due to user not found");
        }

        authUtil.requireSelfOrAdmin(user.getId(), "You are not authorized to delete another User");

        this.userDAO.deleteById(id);

        Cache cache = cacheManager.getCache("userCache");
        if (cache != null) {
            cache.evict(id);
        }

        return ResponseDTO
                .<UserDTO>builder()
                .message("User deleted successfully")
                .build();


    }
}
