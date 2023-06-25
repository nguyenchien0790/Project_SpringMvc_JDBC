package rikkei.academy.service.user;

import rikkei.academy.model.Role;
import rikkei.academy.model.User;
import rikkei.academy.service.IGenericService;

import java.util.Set;

public interface IUserService extends IGenericService<User> {
    boolean existedByUserName(String username);
    boolean existedByEmail(String email);
    User checkLogin(User user);
    Set<Role> findRoleByUserId(int user_id);
    void blockUser(User user);
}
