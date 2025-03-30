package org.scanner.exchangeBingx.controller;

import lombok.AllArgsConstructor;
import org.scanner.exchangeBingx.services.exchangeBingxServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@AllArgsConstructor
@RestController
public class exchangeBingxController {

    private final exchangeBingxServices services;

    @GetMapping("/tickers")
    public Void start() throws URISyntaxException {
        services.tickers();
        return null;
    }

}
