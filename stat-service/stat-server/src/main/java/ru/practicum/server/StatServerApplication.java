package ru.practicum.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ru.practicum.server", "ru.practicum.exception"})
public class StatServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatServerApplication.class, args);
    }
}