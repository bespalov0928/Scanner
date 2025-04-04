package org.scanner.exchangeOkx.controller;

import lombok.AllArgsConstructor;
import org.scanner.exchangeOkx.services.exchangeOkxServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@AllArgsConstructor
@RestController
public class exchangeOkxController {

    private final exchangeOkxServices services;

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
