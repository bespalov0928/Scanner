package org.scanner;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RequestCoinwApplication {
    public static void main(String[] args) {
        SpringApplication.run(RequestCoinwApplication.class, args);
        System.out.printf("http://localhost:8090");
    }
}