package com.example.authservice;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret}")
    private String secret;

    @Value("${security.jwt.token.validity}")
    private long validity;

    public String createJwtToken(Authentication authentication) {
        Date now = new Date();

        List<String> authorities = authentication.getAuthorities().stream().map(a -> a.getAuthority())
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("authorities", authorities)
                .setExpiration(new Date(now.getTime() + validity))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
