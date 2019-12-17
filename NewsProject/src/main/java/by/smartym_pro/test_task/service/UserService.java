package by.smartym_pro.test_task.service;

import by.smartym_pro.test_task.entity.User;

import java.util.List;

public interface UserService {
    User addUser(User user);

    List<User> getAll();

    User findByUsername(String username);

    User findById(Long id);

    void delete(Long id);
}
