package by.smartym_pro.test_task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories(basePackages = "by.smartym_pro.test_task.repository")
@EnableTransactionManagement
@SpringBootApplication
public class NewsProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(NewsProjectApplication.class, args);
    }
}
