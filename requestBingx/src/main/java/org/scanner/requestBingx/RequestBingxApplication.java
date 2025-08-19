package org.scanner.requestBingx;

import jakarta.ws.rs.core.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RequestBingxApplication {
    public static void main(String[] args) {
        SpringApplication.run(RequestBingxApplication.class, args);
        System.out.println("http://localhost:8086");
    }
}
