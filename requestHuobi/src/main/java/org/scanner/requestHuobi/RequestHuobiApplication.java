package org.scanner.requestHuobi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RequestHuobiApplication {
    public static void main(String[] args) {
        SpringApplication.run(RequestHuobiApplication.class, args);
        System.out.println("http://localhost:8088");
    }
}
