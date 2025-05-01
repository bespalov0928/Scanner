package org.scanner;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RequestOkxApplication {
    public static void main(String[] args) {
        SpringApplication.run(RequestOkxApplication.class, args);
        System.out.println("http://localhost:8083");
    }
    //    + Мониторинг сервисов с Micrometer и VictoriaMetrics
    @Bean
    MeterBinder meterBinder() {
        return registr -> {
            Counter.builder("request_okx_counter")
                    .description("Количество запросов OKX")
                    .register(registr);
        };
    }
//    - Мониторинг сервисов с Micrometer и VictoriaMetrics

}