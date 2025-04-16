package org.scanner.requestBybit.controller;

import org.scanner.core.dto.Ticket;
import org.scanner.requestBybit.services.requestBybitServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class requestBybitController {

    @Autowired
    requestBybitServices services;

    @GetMapping("/tickers")
    public List<Ticket> getTickets() throws IOException {
        List<Ticket> rsl = services.getRequest();
        return rsl;
    }
}
