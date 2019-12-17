package by.smartym_pro.test_task.repository;

import by.smartym_pro.test_task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    @Query(value = "SELECT * FROM users u where u.login = ?1",
//            nativeQuery = true)
    User findByUsername(String username);
}
