package by.smartym_pro.test_task.service;

import by.smartym_pro.test_task.entity.Role;
import by.smartym_pro.test_task.entity.User;
import by.smartym_pro.test_task.exception.IncorrectDataException;
import by.smartym_pro.test_task.repository.RoleRepository;
import by.smartym_pro.test_task.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public User addUser(User user) throws IncorrectDataException {
        Optional<Role> role = Optional.of(roleRepository.findByName("ROLE_USER"));
        if(role.isPresent()) {
            user.getRoles().add(role.get());
            Optional<User> registeredUser = Optional.of(userRepository.save(user));
            if(registeredUser.isPresent()) {
                LOGGER.debug("User with name"
                        + registeredUser.get().getFirstname()
                        + " was successfully added!");
                return registeredUser.get();
            }
        }

        LOGGER.error("User with name "
                + user.getFirstname()
                + " wasn't successfully added!");        throw new IncorrectDataException("Database exception");
    }

    @Override
    public User findByUsername(String username) throws IncorrectDataException {
        Optional<User> result = userRepository.findByUsername(username);
        if(result.isPresent()) {
            LOGGER.debug("User with name "
                    + result.get().getFirstname()
                    + " was successfully found!");
            return result.get();
        }

        LOGGER.error("User with name "
                + username + " wasn't found!");
        throw new IncorrectDataException("Such user doesn't exist");
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if(result == null) {
            LOGGER.error("User with id"
                    + id + " wasn't found!");
            return null;
        }

        LOGGER.debug("User with id"
                + id + " was successfully found!");
        return result;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
