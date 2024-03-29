package by.smartym_pro.test_task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;
/**
 * Swagger configuration class.
 *
 * @author Semizhon Roman
 * @version 1.0
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final String PATH_TO_TESTTASK_FOLDER = "by.smartym_pro.test_task";

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage(PATH_TO_TESTTASK_FOLDER))
                .paths(regex("users.*"))
                .build();
    }
}
