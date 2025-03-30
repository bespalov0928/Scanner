package org.scanner.exchangeBingx.services;

import org.scanner.exchangeBingx.component.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

@Service
public class exchangeBingxServices {

    @Autowired
    Client client;

    public void tickers() throws URISyntaxException {
        client.tickers();
    }
}
