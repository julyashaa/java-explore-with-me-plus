package ru.practicum.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.client.RestStatClient;

@Configuration
public class StatClientConfig {

    @Value("${stats-server.url:http://localhost:9090}")
    private String serverUrl;

    @Bean
    public RestStatClient statClient() {
        return new RestStatClient(serverUrl);
    }
}