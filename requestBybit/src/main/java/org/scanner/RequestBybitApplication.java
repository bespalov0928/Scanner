package org.scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RequestBybitApplication {
    public static void main(String[] args) {
        SpringApplication.run(RequestBybitApplication.class, args);
        System.out.println("http://localhost:8084");
    }
}