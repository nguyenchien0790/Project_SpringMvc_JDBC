package rikkei.academy.service.role;

import rikkei.academy.model.Role;
import rikkei.academy.model.RoleName;
import rikkei.academy.model.User;

import java.util.Set;

public interface IRoleSerVice {
    Role findByName(RoleName name);
    void createRoleUser(User user,Role role);

    Set<Role> findRoleByUser(User user);
}
