package org.scanner.exchangeBitget.services;

import org.scanner.exchangeBitget.component.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

@Service
public class exchangeBitgetServices {

    @Autowired
    Client client;

    public void tickers() throws URISyntaxException {
        client.tickers();
    }

}
