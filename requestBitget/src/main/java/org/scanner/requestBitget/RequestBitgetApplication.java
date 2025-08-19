package org.scanner.requestBitget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RequestBitgetApplication {
    public static void main(String[] args) {
        SpringApplication.run(RequestBitgetApplication.class, args);
        System.out.println("http://localhost:8085");
    }
}