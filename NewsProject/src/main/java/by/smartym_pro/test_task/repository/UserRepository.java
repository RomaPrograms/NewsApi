package by.smartym_pro.test_task.repository;

import by.smartym_pro.test_task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
