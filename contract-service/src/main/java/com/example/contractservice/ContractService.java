package com.example.contractservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ContractService {

    public static void main(String[] args) {
        SpringApplication.run(ContractService.class, args);
    }
}

@RestController
class ContractRestController {

    @GetMapping("/contract")
    public String getContractForCustomer() {
        return "best contract";
    }
}
