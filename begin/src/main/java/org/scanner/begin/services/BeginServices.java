package org.scanner.begin.services;

import org.scanner.begin.component.BybitClient;
import org.scanner.begin.dto.*;
import org.scanner.core.dto.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Service
public class BeginServices {

    private Map<String, String> fields = new HashMap<>();

    public BeginServices() {
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
    private BybitClient bybitClient;

//    @Autowired
//    private TicketsConfig ticketsConfig;

    //    @Scheduled(initialDelayString = "${task.initial.delay.millis}", fixedDelayString = "${task.fixed.delay.millis}")
    public void getTickets() {
        bybitClient.getTicketsBybit();
        bybitClient.getTicketsOkx();
        bybitClient.getTicketsBitget();
        bybitClient.getTicketsBingx();
        bybitClient.getTicketsKucoin();
        bybitClient.getTicketsHuobi();
        bybitClient.getTicketsMexc();
        bybitClient.getTicketsCoinW();
        bybitClient.getTicketsGate();
//        processingDeltaBybitOkx();
//        bybitClient.sortedTickets();
//        bybitClient.filterBybitOkx();
    }

    public void processingDelta(String exchange) {
        processingDelta(exchange, "bybit");
        processingDelta(exchange, "okx");
        processingDelta(exchange, "bitget");
        processingDelta(exchange, "bingx");
        processingDelta(exchange, "kucoin");
        processingDelta(exchange, "huobi");
        processingDelta(exchange, "mexc");
        processingDelta(exchange, "coinw");
        processingDelta(exchange, "gate");
//        bybitClient.sortedTickets();
//        bybitClient.filterBybitOkx();
//        filterBybit();
    }

    private void processingDelta(String exchange1, String exchange2) {
        Map<String, Delta> mapDelta = bybitClient.getDeltaMap();
        Map<String, PriceAll> mapPriceAll = bybitClient.getPriceAllMap();
        for (String key : mapPriceAll.keySet()) {
            PriceAll priceAll = mapPriceAll.get(key);
            Field fieldValue1 = null;
            Field fieldValue2 = null;
            Field fieldSymbol = null;
            Double value1 = 0.0;
            Double value2 = 0.0;
            String symbol = "";
            Double rsl = 1.0;
            try {
                fieldValue1 = priceAll.getClass().getDeclaredField(fields.get(exchange1));
                fieldValue2 = priceAll.getClass().getDeclaredField(fields.get(exchange2));
                fieldSymbol = priceAll.getClass().getDeclaredField("symbol");

                value1 = (Double) fieldValue1.get(priceAll);
                value2 = (Double) fieldValue2.get(priceAll);
                symbol = (String) fieldSymbol.get(priceAll);

                Delta deltaObj = mapDelta.get(symbol);
                Double delta = value1 / value2;
                double scale = Math.pow(10, 5);
                if (value1 != 0 && value2 != 0) {
                    rsl = Math.round(delta * scale) / scale;
                }

                if (deltaObj == null) {
                    deltaObj = new Delta();
                    deltaObj.setSymbol(symbol);
                    mapDelta.put(symbol, deltaObj);
                }
                Field field = deltaObj.getClass().getDeclaredField(exchange2);
                field.set(deltaObj, rsl);

            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void filterBybit() {
        Map<String, Delta> mapDelta = bybitClient.getDeltaMap();
        ArrayList<String> arrList = new ArrayList();
        mapDelta.forEach(new BiConsumer<String, Delta>() {
            @Override
            public void accept(String s, Delta deltaBybit) {
                if (deltaBybit.getOkx() == 1 &&
                        deltaBybit.getBitget() == 1 &&
                        deltaBybit.getBingx() == 1 &&
                        deltaBybit.getKucoin() == 1 &&
                        deltaBybit.getHuobi() == 1 &&
                        deltaBybit.getMexc() == 1 &&
                        deltaBybit.getCoinw() == 1 &&
                        deltaBybit.getGate() == 1
                ) arrList.add(s);
            }
        });

        for (String key : arrList) {
            mapDelta.remove(key);
        }
    }

}
