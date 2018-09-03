package com.example.customerservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ContractServiceClient {

    private final RestTemplate restTemplate;

    public ContractServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getDefaultContract")
    public String getContract() {
        String contract = restTemplate.getForObject("http://contract-service/contract", String.class);
        System.out.println("--- contract from contract-service:" + contract);
        return contract;
    }

    public String getDefaultContract() {
        return "No contract";
    }
}
