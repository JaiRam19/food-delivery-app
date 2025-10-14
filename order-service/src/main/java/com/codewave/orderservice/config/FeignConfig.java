package com.codewave.orderservice.config;

import com.codewave.orderservice.exception.CustomFeignErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    public FeignConfig(){
        System.out.println("Feign config class imported..");
    }

    @Bean
    public ErrorDecoder errorDecoder(){
        return new CustomFeignErrorDecoder();
    }
}
