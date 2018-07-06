package com.example.customerservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(JwtSecurityProperties.class)
public class CustomerService {

    public static void main(String[] args) {
        SpringApplication.run(CustomerService.class, args);
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Customer {

    private String firstName, lastName;

}

@RestController
class CustomerRestController {

    @RequestMapping("/customers")
    public List<Customer> customers() {
        return Arrays.asList(new Customer("John", "Doe"));
    }

}