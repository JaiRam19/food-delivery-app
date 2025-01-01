package com.codewave.addressService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder){
        return builder.baseUrl("https://app.zipcodebase.com/api/v1")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

}
