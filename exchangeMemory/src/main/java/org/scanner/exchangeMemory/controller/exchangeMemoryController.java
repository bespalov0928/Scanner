package org.scanner.exchangeMemory.controller;

import org.scanner.core.dto.Resp;
import org.scanner.exchangeMemory.services.ExchangeMemoryServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;

@RestController
public class exchangeMemoryController {

    ExchangeMemoryServices services;

    @PostMapping("/tickers")
    public String tickers(@RequestBody Resp resp) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        services.tickers(resp);
        return "tickers";
    }
}
