package com.example.discoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryService {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryService.class, args);
    }

    @EnableWebSecurity
    static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // by default Spring Security will require that a valid CSRF token to be sent with every request to the app.
            // Since Eureka clients will not generally possess a valid CSRF token you will need to disable this
            // requirement for the /eureka/** endpoints.
            http.csrf().ignoringAntMatchers("/eureka/**")
                    .and()
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .httpBasic();
        }
    }
}

