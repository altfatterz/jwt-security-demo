package com.example.authservice;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret}")
    private String secret;

    @Value("${security.jwt.token.validity}")
    private long validity;

    public String createJwtToken(String username) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(now.getTime() + validity))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
