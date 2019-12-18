package by.smartym_pro.test_task.service;

import by.smartym_pro.test_task.entity.User;
import by.smartym_pro.test_task.exception.IncorrectDataException;

public interface UserService {
    User addUser(User user) throws IncorrectDataException;

    User findByUsername(String username) throws IncorrectDataException;

    User findById(Long id);

    void delete(Long id);
}
