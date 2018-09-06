package com.example.edgeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
@EnableConfigurationProperties(JWTSecurityProperties.class)
public class EdgeService {

    public static void main(String[] args) {
        SpringApplication.run(EdgeService.class, args);
    }

}
