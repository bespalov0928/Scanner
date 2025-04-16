package org.scanner;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class exchangeBybitApplication {
    public static void main(String[] args) {
        SpringApplication.run(exchangeBybitApplication.class, args);
    }

    @Bean
    MeterBinder meterBinder(){
        return registr->{
            Counter.builder("bybit_ticker_count")
                    .description("Количество обращений")
                    .register(registr);
        };
    }
}