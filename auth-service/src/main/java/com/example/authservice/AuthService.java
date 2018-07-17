package com.example.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class AuthService {

    public static void main(String[] args) {
        SpringApplication.run(AuthService.class, args);
    }
}

@RestController
class GreetingController {

    @GetMapping("/")
    public String greet() {
        return "hello";
    }

}
