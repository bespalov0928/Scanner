package org.scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RequestGateApplication {
    public static void main(String[] args) {
        SpringApplication.run(RequestGateApplication.class, args);
        System.out.println("http://localhost:8091");
    }
}