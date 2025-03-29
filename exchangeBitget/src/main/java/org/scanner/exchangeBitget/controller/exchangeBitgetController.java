package org.scanner.exchangeBitget.controller;

import lombok.AllArgsConstructor;
import org.scanner.exchangeBitget.services.exchangeBitgetServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@AllArgsConstructor
@RestController
public class exchangeBitgetController {

    private final exchangeBitgetServices services;

    @GetMapping("/tickers")
    public Void tickers() throws URISyntaxException {
        services.tickers();
        return null;
    }
}
