package sa.mhmdfayedh.CourseConnect.services.v1.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import sa.mhmdfayedh.CourseConnect.entities.Role;
import sa.mhmdfayedh.CourseConnect.repositories.interfaces.RoleDAO;
import sa.mhmdfayedh.CourseConnect.services.v1.interfaces.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleDAO roleDAO;

    @Autowired
    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Cacheable("roleCache")
    public Role findRoleById(int id) {
        return roleDAO.findRoleById(id);
    }

}
