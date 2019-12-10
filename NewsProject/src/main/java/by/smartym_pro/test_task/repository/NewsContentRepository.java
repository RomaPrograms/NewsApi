package by.smartym_pro.test_task.repository;

import by.smartym_pro.test_task.entity.NewsContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsContentRepository extends JpaRepository<NewsContent, Long> {
}
