package com.emsi.sav.serviceagents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ServiceAgentsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAgentsApplication.class, args);
    }
}