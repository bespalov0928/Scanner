package org.scanner.requestBybit.controller;

//import io.micrometer.core.instrument.MeterRegistry;
import org.scanner.core.dto.TicketBasic;
import org.scanner.requestBybit.config.CastomHealthIndicator;
import org.scanner.requestBybit.services.requestBybitServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class requestBybitController {

    @Autowired
    requestBybitServices services;

//    @Autowired
//    MeterRegistry meterRegistry;

    @Autowired
    CastomHealthIndicator healthIndicator;

    @GetMapping("/health")
    public ResponseEntity<Health> getHealth(){
        Health health = healthIndicator.health();
        return ResponseEntity.ok().body(health);
    }

    @GetMapping("/tickers")
    public List<TicketBasic> getTickets() throws IOException {
//        this.meterRegistry.counter("tickers_bybit_count", List.of()).increment();
        List<TicketBasic> rsl = services.getRequest();
        return rsl;
    }
}
