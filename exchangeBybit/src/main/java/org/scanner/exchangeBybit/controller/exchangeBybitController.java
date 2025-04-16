package org.scanner.exchangeBybit.controller;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import org.scanner.exchangeBybit.services.exchangeBybitServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.List;

@AllArgsConstructor
@RestController
public class exchangeBybitController {

    private final exchangeBybitServices services;
    MeterRegistry meterRegistry;

    @GetMapping("/tickers")
    public Void start() throws URISyntaxException {
        this.meterRegistry.counter("tickers_count", List.of()).increment();
        services.tickers();
        return null;
    }

    @GetMapping("/pairs")
    public Void pairs() throws URISyntaxException {
        services.pairs();
        return null;
    }
}
