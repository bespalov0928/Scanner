package org.scanner.requestBitget.controller;

import io.micrometer.core.instrument.MeterRegistry;
import org.scanner.core.dto.TicketBasic;
import org.scanner.requestBitget.services.requestBitgetServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class requestBitgetController {
    @Autowired
    requestBitgetServices services;

//    @Autowired
//    MeterRegistry meterRegistry;

    @GetMapping("/tickers")
    public List<TicketBasic> getTickets() throws IOException {
//        this.meterRegistry.counter("tickers_bitget_count", List.of()).increment();
        List<TicketBasic> rsl = services.getRequest();
        return rsl;
    }

}
