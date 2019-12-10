package by.smartym_pro.test_task.repository;

import by.smartym_pro.test_task.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
