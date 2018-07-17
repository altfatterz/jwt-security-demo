package com.example.authservice.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private JwtSecurityProperties jwtSecurityProperties;

    public JwtTokenProvider(JwtSecurityProperties jwtSecurityProperties) {
        this.jwtSecurityProperties = jwtSecurityProperties;
    }

    public String createJwtToken(Authentication authentication) {
        Date now = new Date();

        List<String> authorities = authentication.getAuthorities().stream().map(a -> a.getAuthority())
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("authorities", authorities)
                .setExpiration(new Date(now.getTime() + jwtSecurityProperties.getToken().getValidity()))
                .signWith(SignatureAlgorithm.HS256, jwtSecurityProperties.getToken().getSecret())
                .compact();
    }
}