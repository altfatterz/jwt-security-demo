package com.example.customerservice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.LocalHostUriTemplateHandler;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AppEndpointTests {

    private TestRestTemplate restTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    @Before
    public void setUp() {
        // need to create our own TestRestTemplate otherwise the RestTemplateCustomizer is used to
        // create the autowired instance which is not good for us
        restTemplate = new TestRestTemplate();
        LocalHostUriTemplateHandler handler = new LocalHostUriTemplateHandler(applicationContext.getEnvironment());
        restTemplate.setUriTemplateHandler(handler);
    }
    @Test
    public void missingJWTToken() {
        ResponseEntity<String> response = restTemplate.getForEntity("/", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void malformedJWTToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer dummy");

        ResponseEntity<String> response = restTemplate.exchange("/", HttpMethod.GET,
                new HttpEntity<>(headers), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void validJWTToken() {
        HttpHeaders headers = new HttpHeaders();

        // created with echo '{"username":"user", "password":"password"}' | http post :8080/login
        headers.add("Authorization", " Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXV0aG9yaXRpZXMiOiJST0xFX1VTRVIifQ.h4exZ-n4RPqhwThw-BVCyt9AxNsUDKe9joKV2QR-2eI");

        ResponseEntity<String> response = restTemplate.exchange("/customers", HttpMethod.GET, new HttpEntity<>(headers), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("[{\"firstName\":\"John\",\"lastName\":\"Doe\",\"contract\":\"hello\"}]");
        assertThat(response.getHeaders().containsKey("Set-Cookie")).isFalse();
    }

}