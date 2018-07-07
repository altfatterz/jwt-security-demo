package com.example.customerservice;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtService {

    private final JwtSecurityProperties jwtSecurityProperties;

    public JwtService(JwtSecurityProperties jwtSecurityProperties) {
        this.jwtSecurityProperties = jwtSecurityProperties;
    }

    public String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean isValidToken(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new JwtException("missing jwt token");
        }
        Jwts.parser().setSigningKey(jwtSecurityProperties.getToken().getSecret()).parseClaimsJws(token);
        return true;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, getAuthorities(claims));
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecurityProperties.getToken().getSecret()).parseClaimsJws(token).getBody();
    }

    private List<GrantedAuthority> getAuthorities(Claims claims) {
        List<String> authorities = claims.get("authorities", List.class);
        return authorities.stream().map((role) -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    }



}
