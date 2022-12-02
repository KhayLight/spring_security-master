package web.service;

import web.model.Role;
import web.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    List<Role> findAllRoles();

    void saveUser(User user, String role);

    void reg(String username, String password);

    User getUserById(long id);

    void deleteUserById(long id);

    User checkUserByUsernameAndPassword(String username, String password);
}
