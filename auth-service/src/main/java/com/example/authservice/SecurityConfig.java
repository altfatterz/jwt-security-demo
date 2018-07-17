package com.example.authservice;

import com.example.authservice.jwt.JwtConfigurer;
import com.example.authservice.jwt.JwtSecurityProperties;
import com.example.authservice.jwt.JwtTokenProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService()  {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password("{noop}password").roles("USER").build());
        manager.createUser(User.withUsername("admin").password("{noop}admin").roles("USER", "ADMIN").build());
        manager.createUser(User.withUsername("manager").password("{noop}manager").roles("MANAGER").build());
        return manager;
    }

    @Configuration
    @Order(1)
    static class ActuatorSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/actuator/**")
                    .authorizeRequests()
                    .anyRequest().hasRole("MANAGER")
                    .and()
                    .httpBasic();
        }

    }

    @Configuration
    @EnableConfigurationProperties(JwtSecurityProperties.class)
    static class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

        private final JwtTokenProvider jwtTokenProvider;

        public ApplicationSecurityConfig(JwtTokenProvider jwtTokenProvider) {
            this.jwtTokenProvider = jwtTokenProvider;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.apply(new JwtConfigurer(jwtTokenProvider));
        }

    }
}
