package com.example.edgeservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTConfiguration {

    private final JWTSecurityProperties jwtSecurityProperties;

    public JWTConfiguration(JWTSecurityProperties jwtSecurityProperties) {
        this.jwtSecurityProperties = jwtSecurityProperties;
    }

    @Bean
    public JWTZuulFilter jwtZuulFilter() {
        return new JWTZuulFilter(jwtSecurityProperties);
    }
}
