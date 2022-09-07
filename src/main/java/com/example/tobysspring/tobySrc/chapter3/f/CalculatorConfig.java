package com.example.tobysspring.tobySrc.chapter3.f;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CalculatorConfig {
    @Bean
    public Calculator calculator() {
        return new Calculator();
    }
}
