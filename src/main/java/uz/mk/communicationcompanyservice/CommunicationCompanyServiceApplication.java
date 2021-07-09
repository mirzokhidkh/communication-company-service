package uz.mk.communicationcompanyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class CommunicationCompanyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunicationCompanyServiceApplication.class, args);
    }

}
