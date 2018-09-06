package com.example.edgeservice;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties("security.jwt")
public class JWTSecurityProperties {

    private String issuer;
    private String audience;
    private String secret;
    private String subject;
    private List<String> authorities;

}
