package org.scanner.begin.services;

import org.scanner.begin.component.RestClient;
import org.scanner.begin.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Service
public class IndexServices {

    private Map<String, String> fields = new HashMap<>();

    public IndexServices() {
        fields.put("bybit", "priceBybit");
        fields.put("okx", "priceOkx");
        fields.put("bitget", "priceBitget");
        fields.put("bingx", "priceBingx");
        fields.put("kucoin", "priceKucoin");
        fields.put("huobi", "priceHuobi");
        fields.put("mexc", "priceMexc");
        fields.put("coinw", "priceCoinW");
        fields.put("gate", "priceGate");
    }

    @Autowired
    private RestClient restClient;

//        @Scheduled(initialDelayString = "${task.initial.delay.millis}", fixedDelayString = "${task.fixed.delay.millis}")
    public void getTickets(String сoinPair) {
        restClient.getTicketsBybit(сoinPair);
        restClient.getTicketsOkx();
        restClient.getTicketsBitget();
        restClient.getTicketsBingx();
        restClient.getTicketsKucoin();
        restClient.getTicketsHuobi();
        restClient.getTicketsMexc();
        restClient.getTicketsCoinW();
        restClient.getTicketsGate();

        restClient.replaceValueCoin();
        restClient.filterPriceAll();

        restClient.sortedTicketPriceMap();

    }
}
