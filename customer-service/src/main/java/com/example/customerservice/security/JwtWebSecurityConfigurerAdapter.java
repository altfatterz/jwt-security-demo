package com.example.customerservice.security;

import com.example.jwt.filter.JwtSecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.example.jwt.filter.JwtHttpSecurityConfigurer.jwt;

@Configuration
public class JwtWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private JwtSecurityProperties jwtSecurityProperties;

    public JwtWebSecurityConfigurerAdapter(JwtSecurityProperties jwtSecurityProperties) {
        this.jwtSecurityProperties = jwtSecurityProperties;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.apply(jwt(jwtSecurityProperties));
    }

}
