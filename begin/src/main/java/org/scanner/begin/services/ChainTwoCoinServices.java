package org.scanner.begin.services;

import org.scanner.begin.component.RestClient;
import org.scanner.begin.dto.ChainTwoCoinDto;
import org.scanner.begin.dto.Delta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.CharSequence.compare;

@Service
public class ChainTwoCoinServices {

    @Autowired
    private RestClient restClient;

    @Autowired
    GeneralServices generalServices;

    ArrayList<String> listExchange;

    public ChainTwoCoinServices() {
        String[] arrExchange = new String[]{"bybit", "okx", "bitget", "bingx", "kucoin", "huobi", "mexc", "coinw", "gate"};
        this.listExchange = new ArrayList<>(Arrays.asList(arrExchange));
    }

    public ChainTwoCoinDto findChainTwoCoin(Double spred, String сoinPair) {
        HashMap<String, Delta> mapDelta = new HashMap<>();

        generalServices.processingDelta("bybit", сoinPair);
        filterDelta(mapDelta, spred, "bybit");

        generalServices.processingDelta("okx", сoinPair);
        filterDelta(mapDelta, spred, "okx");

        generalServices.processingDelta("bitget", сoinPair);
        filterDelta(mapDelta, spred, "bitget");

        generalServices.processingDelta("bingx", сoinPair);
        filterDelta(mapDelta, spred, "bingx");

        generalServices.processingDelta("kucoin", сoinPair);
        filterDelta(mapDelta, spred, "kucoin");

        generalServices.processingDelta("huobi", сoinPair);
        filterDelta(mapDelta, spred, "huobi");

        generalServices.processingDelta("mexc", сoinPair);
        filterDelta(mapDelta, spred, "mexc");

        generalServices.processingDelta("coinw", сoinPair);
        filterDelta(mapDelta, spred, "coinw");

        generalServices.processingDelta("gate", сoinPair);
        filterDelta(mapDelta, spred, "gate");

        ChainTwoCoinDto arrayChain = new ChainTwoCoinDto();
        arrayChain.setSpred(spred);
        arrayChain.setСoinPair(сoinPair);

        ArrayList<Delta> listDelta = new ArrayList<>(mapDelta.values());

        ArrayList<Delta> listFilter = (ArrayList<Delta>) listDelta.stream().filter(new Predicate<Delta>() {
            @Override
            public boolean test(Delta delta) {
                if (delta.bybit.equals(0.0)
                        && delta.okx.equals(0.0)
                        && delta.bitget.equals(0.0)
                        && delta.bingx.equals(0.0)
                        && delta.kucoin.equals(0.0)
                        && delta.huobi.equals(0.0)
                        && delta.mexc.equals(0.0)
                        && delta.coinw.equals(0.0)
                        && delta.gate.equals(0.0)) {
                    return false;
                }
                return true;
            }
        }).collect(Collectors.toList());

        ArrayList<Delta> listRsl = (ArrayList<Delta>) listFilter.stream().sorted((Delta o1, Delta o2) -> compare(o1.symbol, o2.symbol)).collect(Collectors.toList());
        arrayChain.setListChain_v2(listRsl);
        
        return arrayChain;
    }

    private void filterDelta(HashMap<String, Delta> mapSpred, Double spred, String exchangeOne) {
        HashMap<String, Delta> mapDelta = restClient.getDeltaMap();
        for (Map.Entry<String, Delta> entry : mapDelta.entrySet()) {
            Delta delta = entry.getValue();
            try {
                String symbol = (String) delta.getClass().getDeclaredField("symbol").get(delta);
                String exchange = (String) delta.getClass().getDeclaredField("exchange").get(delta);

                for (Field field : delta.getClass().getDeclaredFields()) {
                    if (!listExchange.contains(field.getName())) continue;
                    if (field.getName().equals(delta.exchange)) continue;

                    Double value = (Double) delta.getClass().getDeclaredField(field.getName()).get(delta);

                    if (value.equals(0.0)) continue;
                    if (value.equals(1.0)) continue;
                    if (value.equals(spred + 1.0)) continue;

                    String keyMap = String.format("%s_%s", exchange, symbol);
                    Delta deltaSpred = mapSpred.get(keyMap);
                    if (deltaSpred == null) {
                        deltaSpred = new Delta();
                        deltaSpred.setSymbol(symbol);
                        deltaSpred.setExchange(exchange);
                        mapSpred.put(keyMap, deltaSpred);
                    }
                    if (value < 1.0) {
                        if (1.0 - value < spred) continue;
                    } else {
                        if (value - 1.0 < spred) continue;
                    }
                    Field fieldSpred = deltaSpred.getClass().getDeclaredField(field.getName());
                    fieldSpred.set(deltaSpred, value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

