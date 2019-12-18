package by.smartym_pro.test_task.service;

import by.smartym_pro.test_task.entity.Role;
import by.smartym_pro.test_task.entity.User;
import by.smartym_pro.test_task.entity.UserRoleId;
import by.smartym_pro.test_task.entity.UserRoleKey;
import by.smartym_pro.test_task.exception.IncorrectDataException;
import by.smartym_pro.test_task.repository.RoleRepository;
import by.smartym_pro.test_task.repository.UserRepository;
import by.smartym_pro.test_task.repository.UserRoleIdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleIdRepository userRoleIdRepository;

    @Override
    @Transactional
    public User addUser(User user) throws IncorrectDataException, SQLException {
        Optional<Role> role = Optional.of(roleRepository.findByName("ROLE_USER"));
        if(role.isPresent()) {
            user.getRoles().add(role.get());
            Optional<User> registeredUser = Optional.of(userRepository.save(user));
            if(registeredUser.isPresent()) {
                UserRoleKey userRoleKey = new UserRoleKey();
                UserRoleId userRoleId = new UserRoleId();
                userRoleId.setRole(role.get());
                userRoleId.setUser(user);
                userRoleKey.setRoleId(role.get().getId());
                userRoleKey.setUserId(user.getId());
                userRoleId.setId(userRoleKey);
                userRoleIdRepository.save(userRoleId);
                return registeredUser.get();
            }
        }

        throw new IncorrectDataException("Database exception");
    }

    @Override
    public User findByUsername(String username) throws IncorrectDataException {
        Optional<User> result = userRepository.findByUsername(username);
        if(result.isPresent()) {
            return result.get();
        }
        throw new IncorrectDataException("Such user doesn't exist");
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if(result == null) {
            return null;
        }

        return result;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
