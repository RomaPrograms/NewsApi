package by.smartym_pro.test_task.repository;

import by.smartym_pro.test_task.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
