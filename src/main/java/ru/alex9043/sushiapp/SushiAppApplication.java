package ru.alex9043.sushiapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SushiAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SushiAppApplication.class, args);
    }

}
