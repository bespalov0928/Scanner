package org.scanner.begin.services;

import org.scanner.begin.component.BybitClient;
import org.scanner.begin.dto.DeltaBybit;
import org.scanner.begin.dto.PriceAll;
import org.scanner.core.dto.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Service
public class BeginServices {

    @Autowired
    private BybitClient bybitClient;

//    @Autowired
//    private TicketsConfig ticketsConfig;

    //    @Scheduled(initialDelayString = "${task.initial.delay.millis}", fixedDelayString = "${task.fixed.delay.millis}")
    public void startBybit() {
        System.out.println("startBybit");
        bybitClient.bybitStart();
    }

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

    public void getTicketsBybit() {
        processingDeltaBybitOkx();
        processingDeltaBybitBitget();
        processingDeltaBybitBingx();
        processingDeltaBybitKucoin();
        processingDeltaBybitHuobi();
        processingDeltaBybitMexc();
        processingDeltaBybitCoinW();
        processingDeltaBybitGate();
//        bybitClient.sortedTickets();
//        bybitClient.filterBybitOkx();
        filterBybit();
    }

    private void filterBybit(){
        Map<String, DeltaBybit> mapDeltaBybit = bybitClient.getDeltaBybitMap();
        ArrayList<String> arrList = new ArrayList();
        mapDeltaBybit.forEach(new BiConsumer<String, DeltaBybit>() {
            @Override
            public void accept(String s, DeltaBybit deltaBybit) {
                if (deltaBybit.getOkx()==1 &&
                        deltaBybit.getBitget()==1 &&
                        deltaBybit.getBingx()==1 &&
                        deltaBybit.getKucoin()==1 &&
                        deltaBybit.getHuobi()==1 &&
                        deltaBybit.getMexc()==1 &&
                        deltaBybit.getCoinW()==1 &&
                        deltaBybit.getGate()==1
                ) arrList.add(s);
            }
        });

        for (String key : arrList) {
            mapDeltaBybit.remove(key);
        }
    }

    private void processingDeltaBybitOkx_old(){
        Map<String, Ticket> mapTickets = bybitClient.getTicketsMap();
        for (String key : mapTickets.keySet()) {
            Ticket ticket = mapTickets.get(key);
            if (ticket.getPriceBybit()==0 || ticket.getPriceOkx()==0) continue;
            ticket.setDeltaBybitOkx(ticket.getPriceBybit()/ ticket.getPriceOkx());
        }

    }

    private void processingDeltaBybitOkx(){
        Map<String, DeltaBybit> mapDeltaBybit = bybitClient.getDeltaBybitMap();
        Map<String, PriceAll> mapPriceAll = bybitClient.getPriceAllMap();
        for (String key : mapPriceAll.keySet()) {
            PriceAll priceAll = mapPriceAll.get(key);
            if (priceAll.getPriceBybit()==0 || priceAll.getPriceOkx()==0) continue;

            DeltaBybit deltaBybit = mapDeltaBybit.get(priceAll.getSymbol());
            Double delta = priceAll.getPriceBybit()/ priceAll.getPriceOkx();
            double scale = Math.pow(10, 5);

            if (deltaBybit == null) {
                DeltaBybit newDeltaBybit = new DeltaBybit();
                newDeltaBybit.setSymbol(priceAll.getSymbol());
                newDeltaBybit.setOkx(Math.round(delta * scale) / scale);
                mapDeltaBybit.put(newDeltaBybit.getSymbol(), newDeltaBybit);
            } else {
                deltaBybit.setOkx(Math.round(delta * scale) / scale);
            }
        }
    }
    private void processingDeltaBybitBitget() {
        Map<String, DeltaBybit> mapDeltaBybit = bybitClient.getDeltaBybitMap();
        Map<String, PriceAll> mapPriceAll = bybitClient.getPriceAllMap();
        for (String key : mapPriceAll.keySet()) {
            PriceAll priceAll = mapPriceAll.get(key);
            if (priceAll.getPriceBybit()==0 || priceAll.getPriceBitget()==0) continue;

            DeltaBybit deltaBybit = mapDeltaBybit.get(priceAll.getSymbol());
            Double delta = priceAll.getPriceBybit()/ priceAll.getPriceBitget();
            double scale = Math.pow(10, 5);

            if (deltaBybit == null) {
                DeltaBybit newDeltaBybit = new DeltaBybit();
                newDeltaBybit.setSymbol(priceAll.getSymbol());
                newDeltaBybit.setBitget(Math.round(delta * scale) / scale);
                mapDeltaBybit.put(newDeltaBybit.getSymbol(), newDeltaBybit);
            } else {
                deltaBybit.setBitget(Math.round(delta * scale) / scale);
            }
        }
    }
    private void processingDeltaBybitBingx() {
        Map<String, DeltaBybit> mapDeltaBybit = bybitClient.getDeltaBybitMap();
        Map<String, PriceAll> mapPriceAll = bybitClient.getPriceAllMap();
        for (String key : mapPriceAll.keySet()) {
            PriceAll priceAll = mapPriceAll.get(key);
            if (priceAll.getPriceBybit()==0 || priceAll.getPriceBingx()==0) continue;

            DeltaBybit deltaBybit = mapDeltaBybit.get(priceAll.getSymbol());
            Double delta = priceAll.getPriceBybit()/ priceAll.getPriceBingx();
            double scale = Math.pow(10, 5);

            if (deltaBybit == null) {
                DeltaBybit newDeltaBybit = new DeltaBybit();
                newDeltaBybit.setSymbol(priceAll.getSymbol());
                newDeltaBybit.setBingx(Math.round(delta * scale) / scale);
                mapDeltaBybit.put(newDeltaBybit.getSymbol(), newDeltaBybit);
            } else {
                deltaBybit.setBingx(Math.round(delta * scale) / scale);
            }
        }
    }
    private void processingDeltaBybitKucoin() {
        Map<String, DeltaBybit> mapDeltaBybit = bybitClient.getDeltaBybitMap();
        Map<String, PriceAll> mapPriceAll = bybitClient.getPriceAllMap();
        for (String key : mapPriceAll.keySet()) {
            PriceAll priceAll = mapPriceAll.get(key);
            if (priceAll.getPriceBybit()==0 || priceAll.getPriceKucoin()==0) continue;

            DeltaBybit deltaBybit = mapDeltaBybit.get(priceAll.getSymbol());
            Double delta = priceAll.getPriceBybit()/ priceAll.getPriceKucoin();
            double scale = Math.pow(10, 5);

            if (deltaBybit == null) {
                DeltaBybit newDeltaBybit = new DeltaBybit();
                newDeltaBybit.setSymbol(priceAll.getSymbol());
                newDeltaBybit.setKucoin(Math.round(delta * scale) / scale);
                mapDeltaBybit.put(newDeltaBybit.getSymbol(), newDeltaBybit);
            } else {
                deltaBybit.setKucoin(Math.round(delta * scale) / scale);
            }
        }
    }
    private void processingDeltaBybitHuobi() {
        Map<String, DeltaBybit> mapDeltaBybit = bybitClient.getDeltaBybitMap();
        Map<String, PriceAll> mapPriceAll = bybitClient.getPriceAllMap();
        for (String key : mapPriceAll.keySet()) {
            PriceAll priceAll = mapPriceAll.get(key);
            if (priceAll.getPriceBybit()==0 || priceAll.getPriceHuobi()==0) continue;

            DeltaBybit deltaBybit = mapDeltaBybit.get(priceAll.getSymbol());
            Double delta = priceAll.getPriceBybit()/ priceAll.getPriceHuobi();
            double scale = Math.pow(10, 5);

            if (deltaBybit == null) {
                DeltaBybit newDeltaBybit = new DeltaBybit();
                newDeltaBybit.setSymbol(priceAll.getSymbol());
                newDeltaBybit.setHuobi(Math.round(delta * scale) / scale);
                mapDeltaBybit.put(newDeltaBybit.getSymbol(), newDeltaBybit);
            } else {
                deltaBybit.setHuobi(Math.round(delta * scale) / scale);
            }
        }
    }
    private void processingDeltaBybitMexc() {
        Map<String, DeltaBybit> mapDeltaBybit = bybitClient.getDeltaBybitMap();
        Map<String, PriceAll> mapPriceAll = bybitClient.getPriceAllMap();
        for (String key : mapPriceAll.keySet()) {
            PriceAll priceAll = mapPriceAll.get(key);
            if (priceAll.getPriceBybit()==0 || priceAll.getPriceMexc()==0) continue;

            DeltaBybit deltaBybit = mapDeltaBybit.get(priceAll.getSymbol());
            Double delta = priceAll.getPriceBybit()/ priceAll.getPriceMexc();
            double scale = Math.pow(10, 5);

            if (deltaBybit == null) {
                DeltaBybit newDeltaBybit = new DeltaBybit();
                newDeltaBybit.setSymbol(priceAll.getSymbol());
                newDeltaBybit.setMexc(Math.round(delta * scale) / scale);
                mapDeltaBybit.put(newDeltaBybit.getSymbol(), newDeltaBybit);
            } else {
                deltaBybit.setMexc(Math.round(delta * scale) / scale);
            }
        }
    }
    private void processingDeltaBybitCoinW() {
        Map<String, DeltaBybit> mapDeltaBybit = bybitClient.getDeltaBybitMap();
        Map<String, PriceAll> mapPriceAll = bybitClient.getPriceAllMap();
        for (String key : mapPriceAll.keySet()) {
            PriceAll priceAll = mapPriceAll.get(key);
            if (priceAll.getPriceBybit()==0 || priceAll.getPriceCoinW()==0) continue;

            DeltaBybit deltaBybit = mapDeltaBybit.get(priceAll.getSymbol());
            Double delta = priceAll.getPriceBybit()/ priceAll.getPriceCoinW();
            double scale = Math.pow(10, 5);

            if (deltaBybit == null) {
                DeltaBybit newDeltaBybit = new DeltaBybit();
                newDeltaBybit.setSymbol(priceAll.getSymbol());
                newDeltaBybit.setCoinW(Math.round(delta * scale) / scale);
                mapDeltaBybit.put(newDeltaBybit.getSymbol(), newDeltaBybit);
            } else {
                deltaBybit.setCoinW(Math.round(delta * scale) / scale);
            }
        }
    }
    private void processingDeltaBybitGate() {
        Map<String, DeltaBybit> mapDeltaBybit = bybitClient.getDeltaBybitMap();
        Map<String, PriceAll> mapPriceAll = bybitClient.getPriceAllMap();
        for (String key : mapPriceAll.keySet()) {
            PriceAll priceAll = mapPriceAll.get(key);
            if (priceAll.getPriceBybit()==0 || priceAll.getPriceGate()==0) continue;

            DeltaBybit deltaBybit = mapDeltaBybit.get(priceAll.getSymbol());
            Double delta = priceAll.getPriceBybit()/ priceAll.getPriceGate();
            double scale = Math.pow(10, 5);

            if (deltaBybit == null) {
                DeltaBybit newDeltaBybit = new DeltaBybit();
                newDeltaBybit.setSymbol(priceAll.getSymbol());
                newDeltaBybit.setGate(Math.round(delta * scale) / scale);
                mapDeltaBybit.put(newDeltaBybit.getSymbol(), newDeltaBybit);
            } else {
                deltaBybit.setGate(Math.round(delta * scale) / scale);
            }
        }
    }

    private void processingDeltaOkxBitget() {
    }
    private void processingDeltaOkxBingx() {
    }
    private void processingDeltaOkxKucoin() {
    }
    private void processingDeltaOkxHuobi() {
    }
    private void processingDeltaOkxMexc() {
    }
    private void processingDeltaOkxCoinW() {
    }
    private void processingDeltaOkxGate() {
    }

    private void processingDeltaBitgetBingx() {
    }
    private void processingDeltaBitgetKucoin() {
    }
    private void processingDeltaBitgetHuobi() {
    }
    private void processingDeltaBitgetMexc() {
    }
    private void processingDeltaBitgetCoinW() {
    }
    private void processingDeltaBitgetGate() {
    }

    private void processingDeltaBingxKucoin() {
    }
    private void processingDeltaBingxHuobi() {
    }
    private void processingDeltaBingxMexc() {
    }
    private void processingDeltaBingxCoinW() {
    }
    private void processingDeltaBingxGate() {
    }

    private void processingDeltaKucoinHuobi() {
    }
    private void processingDeltaKucoinMexc() {
    }
    private void processingDeltaKucoinCoinW() {
    }
    private void processingDeltaKucoinGate() {
    }

    private void processingDeltaHuobiMexc() {
    }
    private void processingDeltaHuobiCoinW() {
    }
    private void processingDeltaHuobiGate() {
    }

    private void processingDeltaMexcCoinW() {
    }
    private void processingDeltaMexcGate() {
    }

    private void processingDeltaCoinWGate() {
    }

}
