package org.scanner.reports;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ReportsApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(ReportsApplication.class, args);
        System.out.println("Go to http://localhost:8081/index");
    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(ReportsApplication.class);
//    }
}
