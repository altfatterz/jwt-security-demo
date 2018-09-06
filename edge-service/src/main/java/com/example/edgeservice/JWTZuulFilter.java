package com.example.edgeservice;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;

@Slf4j
public class JWTZuulFilter extends ZuulFilter {

    private final JWTSecurityProperties jwtSecurityProperties;

    public JWTZuulFilter(JWTSecurityProperties jwtSecurityProperties) {
        this.jwtSecurityProperties = jwtSecurityProperties;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setDebugRequest(true);

        HttpServletRequest request = ctx.getRequest();

        Enumeration<String> headerNames = request.getHeaderNames();

        log.info("Headers:");
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();

            log.info(headerName + ":" + request.getHeader(headerName));
        }

        System.out.println("Authorities:" + jwtSecurityProperties.getAuthorities());

        ctx.addZuulRequestHeader(
                HttpHeaders.AUTHORIZATION,
                "Bearer " + Jwts.builder()
                        .setSubject(jwtSecurityProperties.getSubject())
                        .claim("authorities", jwtSecurityProperties.getAuthorities())
                        .setAudience(jwtSecurityProperties.getAudience())
                        .setIssuer(jwtSecurityProperties.getIssuer())
                        .signWith(SignatureAlgorithm.HS256, jwtSecurityProperties.getSecret())
                        .setExpiration(Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant()))
                        .compact()
        );

        return null;
    }

}
