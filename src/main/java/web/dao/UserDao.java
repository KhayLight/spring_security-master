package web.dao;


import web.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    List<User> findAll();

    Optional<User> find(Long id);

    void save(User user);

    void delete(User entity);

}
