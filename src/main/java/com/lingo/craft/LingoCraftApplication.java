package com.lingo.craft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LingoCraftApplication {
    public static void main(String[] args) {
        // Test commit
        SpringApplication.run(LingoCraftApplication.class, args);
    }
}
