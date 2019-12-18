package by.smartym_pro.test_task.service;

import by.smartym_pro.test_task.entity.Role;
import by.smartym_pro.test_task.entity.User;
import by.smartym_pro.test_task.entity.UserRoleId;
import by.smartym_pro.test_task.entity.UserRoleKey;
import by.smartym_pro.test_task.repository.RoleRepository;
import by.smartym_pro.test_task.repository.UserRepository;
import by.smartym_pro.test_task.repository.UserRoleIdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleIdRepository userRoleIdRepository;

    @Transactional
    @Override
    public User addUser(User user) {
        Role role = roleRepository.findByName("USER");
        user.getRoles().add(role);
        User registeredUser = userRepository.save(user);
        UserRoleKey userRoleKey = new UserRoleKey();
        UserRoleId userRoleId = new UserRoleId();
//        userRoleId.setRole(role);
//        userRoleId.setUser(user);
        userRoleKey.setRoleId(role.getId());
        userRoleKey.setUserId(user.getId());
        userRoleId.setId(userRoleKey);
        userRoleIdRepository.save(userRoleId);
        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();

        return result;
    }

    //TODO i need to use optionals, it's important
    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        return result;
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
