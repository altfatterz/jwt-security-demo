package com.example.authservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class HttpSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService()  {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password("{noop}password").roles("USER").build());
        manager.createUser(User.withUsername("admin").password("{noop}admin").roles("USER", "ADMIN").build());
        manager.createUser(User.withUsername("manager").password("{noop}manager").roles("MANAGER").build());
        return manager;
    }

    // used by spring boot admin client see WebSecurityConfiguration.setFilterChainProxySecurityConfigurer
    @Configuration
    @Order(1)
    public static class ActuatorWebSecurityConfig extends WebSecurityConfigurerAdapter {

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
    public static class LoginWebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private JwtTokenProvider jwtTokenProvider;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    // otherwise 403, Could not verify the provided CSRF token because your session was not found.
                    .csrf().disable()
                    .addFilter(new CustomUsernamePasswordAuthenticationFilter(authenticationManager(), jwtTokenProvider))
                    .authorizeRequests().anyRequest().authenticated();
        }
    }


}
