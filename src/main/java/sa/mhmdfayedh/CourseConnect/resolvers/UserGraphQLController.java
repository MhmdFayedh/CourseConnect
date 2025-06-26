package sa.mhmdfayedh.CourseConnect.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import sa.mhmdfayedh.CourseConnect.common.mappers.UserMapper;
import sa.mhmdfayedh.CourseConnect.dto.v1.UserDTO;
import sa.mhmdfayedh.CourseConnect.entities.User;
import sa.mhmdfayedh.CourseConnect.repositories.interfaces.UserDAO;

import java.util.List;

@Controller
public class UserGraphQLController  {
    private UserDAO userDAO;

    @Autowired
    public UserGraphQLController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @QueryMapping
    public List<UserDTO> users(){
        List<User> users = userDAO.findAll();
        return UserMapper.toDTOList(users.stream().toList());
    }
}
