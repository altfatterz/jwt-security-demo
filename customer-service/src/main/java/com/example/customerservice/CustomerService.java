package com.example.customerservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableCircuitBreaker
public class CustomerService {

    public static void main(String[] args) {
        SpringApplication.run(CustomerService.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

}


@Data
@AllArgsConstructor
@NoArgsConstructor
class Customer {

    private String firstName, lastName, contract;

}

@RestController
@Slf4j
class CustomerRestController {

    private final ContractServiceClient contractServiceClient;

    public CustomerRestController(ContractServiceClient contractServiceClient) {
        this.contractServiceClient = contractServiceClient;
    }

    @GetMapping("/customers")
    public List<Customer> customers(HttpServletRequest request) {
        logHeaders(request);
        return Arrays.asList(new Customer("John", "Doe", "hello"));
    }

    @GetMapping("/customers/{customerId}")
    @Secured("ROLE_ADMIN")
    public Customer customer(HttpServletRequest request, @PathVariable String customerId) {
        logHeaders(request);
        return new Customer("Walter", "White", contractServiceClient.getContract());
    }

    private void logHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        log.info("Headers:");
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            log.info(headerName + ":" + request.getHeader(headerName));
        }
    }

}