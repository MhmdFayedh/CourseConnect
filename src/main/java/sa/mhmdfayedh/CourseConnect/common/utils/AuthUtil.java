package sa.mhmdfayedh.CourseConnect.common.utils;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import sa.mhmdfayedh.CourseConnect.common.enums.RoleEnum;
import sa.mhmdfayedh.CourseConnect.entities.User;
import sa.mhmdfayedh.CourseConnect.repositories.interfaces.UserDAO;


@Component
public class AuthUtil {
    private final UserDAO userDAO;

    public AuthUtil(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userDAO.findByEmail(auth.getName());
    }

    public boolean isAdmin(User user) {
        return user.getRole().getName().equals(RoleEnum.ROLE_ADMIN.getDisplyName());
    }

    public void requireSelfOrAdmin(int userID, String message) {
        User currentUser = getCurrentUser();

        if (currentUser == null) {
            throw new BadCredentialsException("Invalid authentication");
        }
        if (userID != currentUser.getId() && !isAdmin(currentUser)) {
            throw new AccessDeniedException(message);
        }
    }
}
