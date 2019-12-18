package by.smartym_pro.test_task.repository;

import by.smartym_pro.test_task.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    Optional<List<News>> findByTopic(List<String> topic);
}
