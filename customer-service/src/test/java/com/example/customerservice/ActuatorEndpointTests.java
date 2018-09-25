package com.example.customerservice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.LocalHostUriTemplateHandler;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ActuatorEndpointTests {

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
    public void missingCredentials() {
        ResponseEntity<String> health = restTemplate.getForEntity("/actuator/health", String.class);
        assertThat(health.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void wrongCredentials() {
        ResponseEntity<String> health = restTemplate.withBasicAuth("wrong", "wrong")
                .getForEntity("/actuator/health", String.class);
        assertThat(health.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void forbidden() {
        ResponseEntity<String> health = restTemplate.withBasicAuth("dummy", "dummy")
                .getForEntity("/actuator/health", String.class);
        assertThat(health.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void valid() {
        ResponseEntity<String> health = restTemplate.withBasicAuth("user", "password")
                .getForEntity("/actuator/health", String.class);
        assertThat(health.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(health.getHeaders().containsKey("Set-Cookie")).isFalse();
    }

    @TestConfiguration
    static class Config {

        @Bean
        public UserDetailsService userDetailsService() {
            UserDetails user = User.withUsername("user").password("{noop}password").roles("MANAGER").build();
            UserDetails dummy = User.withUsername("dummy").password("{noop}dummy").roles("NON_MANAGER").build();
            return new InMemoryUserDetailsManager(user, dummy);
        }

    }
}