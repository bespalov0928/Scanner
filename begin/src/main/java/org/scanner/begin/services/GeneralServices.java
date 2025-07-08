package org.scanner.begin.services;

import org.scanner.begin.component.RestClient;
import org.scanner.begin.dto.Delta;
import org.scanner.begin.dto.PriceAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Service
public class GeneralServices {

    @Autowired
    private RestClient restClient;

    private Map<String, String> fields = new HashMap<>();

    public GeneralServices() {
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

    public void processingDelta(String exchange) {
        Map<String, Delta> mapDelta = restClient.getDeltaMap();
        mapDelta.clear();

        processingDeltaSolution(exchange, "bybit", "");
        processingDeltaSolution(exchange, "okx", "");
        processingDeltaSolution(exchange, "bitget", "");
        processingDeltaSolution(exchange, "bingx", "");
        processingDeltaSolution(exchange, "kucoin", "");
        processingDeltaSolution(exchange, "huobi", "");
        processingDeltaSolution(exchange, "mexc", "");
        processingDeltaSolution(exchange, "coinw", "");
        processingDeltaSolution(exchange, "gate", "");
        restClient.sortedDeltaMap();
//        bybitClient.sortedTickets();
//        bybitClient.filterBybitOkx();
//        filterBybit();
    }

    public void processingDelta(String exchange, String сoinPair) {
        Map<String, Delta> mapDelta = restClient.getDeltaMap();
        mapDelta.clear();

        processingDeltaSolution(exchange, "bybit", сoinPair);
        processingDeltaSolution(exchange, "okx", сoinPair);
        processingDeltaSolution(exchange, "bitget", сoinPair);
        processingDeltaSolution(exchange, "bingx", сoinPair);
        processingDeltaSolution(exchange, "kucoin", сoinPair);
        processingDeltaSolution(exchange, "huobi", сoinPair);
        processingDeltaSolution(exchange, "mexc", сoinPair);
        processingDeltaSolution(exchange, "coinw", сoinPair);
        processingDeltaSolution(exchange, "gate", сoinPair);
    }

    private void processingDeltaSolution(String exchange1, String exchange2, String сoinPair) {
        if (exchange1.equals(exchange2)) return;
        Map<String, PriceAll> mapPriceAll = restClient.getTicketsPriceMap();
        if (!сoinPair.isEmpty()) {
            PriceAll priceAll = mapPriceAll.get(сoinPair);
            processingAddDeltaMap(priceAll, exchange1, exchange2);
        } else {
            for (String key : mapPriceAll.keySet()) {
                PriceAll priceAll = mapPriceAll.get(key);
                processingAddDeltaMap(priceAll, exchange1, exchange2);
            }
        }

    }

    private void processingAddDeltaMap(PriceAll priceAll, String exchange1, String exchange2) {
        Map<String, Delta> mapDelta = restClient.getDeltaMap();

        Field fieldValue1 = null;
        Field fieldValue2 = null;
        Field fieldSymbol = null;
        Double value1 = 0.0;
        Double value2 = 0.0;
        String symbol = "";
        Double rsl = 0.0;
        try {
            fieldValue1 = priceAll.getClass().getDeclaredField(fields.get(exchange1));
            fieldValue2 = priceAll.getClass().getDeclaredField(fields.get(exchange2));
            fieldSymbol = priceAll.getClass().getDeclaredField("symbol");

            value1 = (Double) fieldValue1.get(priceAll);
            value2 = (Double) fieldValue2.get(priceAll);
            symbol = (String) fieldSymbol.get(priceAll);

            if (value1.equals(0.0) || value2.equals(0.0)) return;

            Delta deltaObj = mapDelta.get(symbol);
            Double delta = value1 > value2 ? value1 / value2 : value2 / value1;
            double scale = Math.pow(10, 5);
            if (value1 != 0 && value2 != 0) {
                rsl = Math.round(delta * scale) / scale;
            }

            if (deltaObj == null) {
                deltaObj = new Delta();
                deltaObj.setSymbol(symbol);
                mapDelta.put(symbol, deltaObj);
            }
            deltaObj.setExchange(exchange1);

            Field field = deltaObj.getClass().getDeclaredField(exchange2);
            field.set(deltaObj, rsl);

            Field fieldExchange = deltaObj.getClass().getDeclaredField("exchange");
            fieldExchange.set(deltaObj, exchange1);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
