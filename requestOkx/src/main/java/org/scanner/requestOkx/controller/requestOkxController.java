package org.scanner.requestOkx.controller;

import io.micrometer.core.instrument.MeterRegistry;
import org.scanner.core.dto.Ticket;
import org.scanner.requestOkx.services.requestOkxServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@RestController
public class requestOkxController {

    @Autowired
    requestOkxServices services;

    @Autowired
    MeterRegistry meterRegistry;

    @GetMapping("/tickers")
    public List<Ticket> getTickets() throws IOException {
        this.meterRegistry.counter("tickers_okx_count", List.of()).increment();
        List<Ticket> rsl = services.getRequest();
        return rsl;
    }


}
