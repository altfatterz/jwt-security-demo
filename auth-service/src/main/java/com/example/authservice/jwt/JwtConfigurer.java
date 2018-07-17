package com.example.authservice.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigurer extends AbstractHttpConfigurer<JwtConfigurer, HttpSecurity> {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtConfigurer(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void init(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .csrf().disable()
                .authorizeRequests()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(HttpSecurity http) {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilterBefore(new JsonUsernamePasswordAuthenticationFilter(authenticationManager, jwtTokenProvider),
                UsernamePasswordAuthenticationFilter.class);
    }
}
