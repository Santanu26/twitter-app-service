package com.microservices.demo.kafka.admin.config.client;

import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientConfig {
    @Bean
    WebClient webClient() {
        return WebClient.builder().build();
    }
}
