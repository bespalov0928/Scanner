package org.scanner.requestKucoin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RequestKucoinApplication {
    public static void main(String[] args) {
        SpringApplication.run(RequestKucoinApplication.class, args);
        System.out.println("http://localhost:8087");
    }
}
