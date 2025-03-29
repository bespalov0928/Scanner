package org.scanner.exchangeBybit.controller;

import lombok.AllArgsConstructor;
import org.scanner.exchangeBybit.services.exchangeBybitServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@AllArgsConstructor
@RestController
public class exchangeBybitController {

    private final exchangeBybitServices services;

    @GetMapping("/tickers")
    public Void start() throws URISyntaxException {
        services.tickers();
        return null;
    }

    @GetMapping("/pairs")
    public Void pairs() throws URISyntaxException {
        services.pairs();
        return null;
    }
}
