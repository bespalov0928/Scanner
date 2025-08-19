package org.scanner.requestMexc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RequestMexcApplication {
    public static void main(String[] args) {
        SpringApplication.run(RequestMexcApplication.class, args);
        System.out.println("http://localhost:8089");
    }
}
