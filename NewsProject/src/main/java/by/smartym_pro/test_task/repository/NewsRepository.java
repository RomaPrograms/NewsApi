package by.smartym_pro.test_task.repository;

import by.smartym_pro.test_task.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
