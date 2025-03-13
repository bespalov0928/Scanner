package org.scanner.bybit.controller;

import lombok.AllArgsConstructor;
import org.scanner.bybit.services.RequestsServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class BybitController {

    private final RequestsServices requestsServices;

    @GetMapping("/start")
    public void start() {
        requestsServices.runBybit();
    }
}
