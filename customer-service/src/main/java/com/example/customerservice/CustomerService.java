package com.example.customerservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
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

    @GetMapping("/customers")
    public List<Customer> customers() {
        return Arrays.asList(new Customer("John", "Doe"));
    }

    @GetMapping("/customers/{customerId}")
    @Secured("ROLE_ADMIN")
    public Customer customer(@PathVariable String customerId) {
        return new Customer("Walter", "White");
    }


}