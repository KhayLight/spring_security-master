package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.RoleDao;
import web.dao.UserDao;
import web.model.Role;
import web.model.User;

import javax.persistence.PersistenceException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserDao userDao;


    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public List<Role> findAllRoles() {
        return roleDao.findAll();
    }

    @Override
    public void saveUser(User user, String role) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();

        roles.add(roleDao.findRoleByAuthority(role));
        user.setRoles(roles);
        userDao.save(user);
    }

    @Override
    public void reg(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        saveUser(user, "ROLE_USER");

    }

    @Override
    public User getUserById(long id) {
        return userDao.find(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(String.format("User with ID = %d not found", id), 1));
    }

    @Override
    public void deleteUserById(long id) {
        Optional<User> user = userDao.find(id);
        if (user.isPresent()) {
            try {
                userDao.delete(user.get());
            } catch (PersistenceException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public User checkUserByUsernameAndPassword(String username, String password) {
        return userDao.findByUsernameAndPassword(username, password);
    }
}
