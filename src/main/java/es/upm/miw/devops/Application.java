package es.upm.miw.devops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "es.upm.miw.devops.infrastructure.data.models")
@EnableJpaRepositories(basePackages = "es.upm.miw.devops.infrastructure.data.daos")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}