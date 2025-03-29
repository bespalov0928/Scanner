package org.scanner.exchangeBybit.services;

import org.scanner.exchangeBybit.component.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

@Service
public class exchangeBybitServices {

    @Autowired
    Client client;

    public void tickers() throws URISyntaxException {
        client.tickers();
    }

    public void pairs() throws URISyntaxException {
        client.pairs();
    }
}
