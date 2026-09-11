package es.upm.miw.devops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class}) // Not API: /error
@EnableJpaRepositories(basePackages = "es.upm.miw.devops.data.daos")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);// mvn clean spring-boot:run
    }
}
