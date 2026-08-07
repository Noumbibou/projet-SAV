package com.emsi.sav.servicetickets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ServiceTicketsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceTicketsApplication.class, args);
    }
}