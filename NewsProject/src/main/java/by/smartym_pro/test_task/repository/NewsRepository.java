package by.smartym_pro.test_task.repository;

import by.smartym_pro.test_task.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("select n from news n where n.topic = :name")
    News findByTopic(@Param("topic") String topic);
}
