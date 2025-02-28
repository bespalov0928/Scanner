package org.scanner.begin.controller;

import org.scanner.begin.component.BybitClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeginController {

    @Autowired
    private BybitClient bybitClient;

    @GetMapping("/bybit")
    public String startBybit(){
        bybitClient.bybitStart();
        return "ok";
    }
}
