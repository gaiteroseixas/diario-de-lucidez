package com.diario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DiarioApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiarioApplication.class, args);
    }
}