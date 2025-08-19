package org.scanner.begin.component;

import lombok.Getter;
import org.scanner.begin.checkCoin.ArrayReplaceValueCoin;
import org.scanner.begin.checkCoin.ReplaceValueCoin;
import org.scanner.begin.dto.*;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
@Component
public class RestClient {
    private final RestTemplate restTemplate;
    HashMap<String, PriceAll> ticketsPriceMap = new HashMap<>();
    HashMap<String, Delta> deltaMap = new HashMap<>();

    public RestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void getTicketsBybit(String сoinPair) {
        Status status = checkStatusService("http://bybit-request/health");
        if (!status.equals(Status.UP)) {
            return;
        }
        String url = сoinPair.isEmpty() ? "http://bybit-request/tickers/_" : "http://bybit-request/tickers/" + сoinPair;
        List<LinkedHashMap> tickets = restTemplate.getForObject(url, List.class);

        for (LinkedHashMap ticket : tickets) {
            PriceAll priceAllKey = ticketsPriceMap.get(ticket.get("symbol"));
            if (priceAllKey == null) {
                PriceAll newPriceAll = new PriceAll();
                newPriceAll.setSymbol((String) ticket.get("symbol"));
                newPriceAll.setPriceBybit((Double) ticket.get("price"));
                newPriceAll.setPriceBybitText((String) ticket.get("priceText"));
                ticketsPriceMap.put((String) ticket.get("symbol"), newPriceAll);
            } else {
                priceAllKey.setPriceBybit((Double) ticket.get("price"));
                priceAllKey.setPriceBybitText((String) ticket.get("priceText"));
            }
        }
    }

    public void getTicketsOkx() {
        Status status = checkStatusService("http://okx-request/health");
        if (!status.equals(Status.UP)) {
            return;
        }
        List<LinkedHashMap> tickets = restTemplate.getForObject("http://okx-request/tickers", List.class);

        for (LinkedHashMap ticket : tickets) {
            PriceAll priceAllKey = ticketsPriceMap.get(ticket.get("symbol"));
            if (priceAllKey == null) {
                PriceAll newPriceAll = new PriceAll();
                newPriceAll.setSymbol((String) ticket.get("symbol"));
                newPriceAll.setPriceOkx((Double) ticket.get("price"));
                newPriceAll.setPriceOkxText((String) ticket.get("priceText"));
                ticketsPriceMap.put((String) ticket.get("symbol"), newPriceAll);
            } else {
                priceAllKey.setPriceOkx((Double) ticket.get("price"));
                priceAllKey.setPriceOkxText((String) ticket.get("priceText"));
            }
        }
    }

    public void getTicketsBitget() {
        Status status = checkStatusService("http://bitget-request/health");
        if (!status.equals(Status.UP)) {
            return;
        }
        List<LinkedHashMap> tickets = restTemplate.getForObject("http://bitget-request/tickers", List.class);

        for (LinkedHashMap ticket : tickets) {
            PriceAll priceAllKey = ticketsPriceMap.get(ticket.get("symbol"));
            if (priceAllKey == null) {
                PriceAll newPriceAll = new PriceAll();
                newPriceAll.setSymbol((String) ticket.get("symbol"));
                newPriceAll.setPriceBitget((Double) ticket.get("price"));
                newPriceAll.setPriceBitgetText((String) ticket.get("priceText"));
                ticketsPriceMap.put((String) ticket.get("symbol"), newPriceAll);
            } else {
                priceAllKey.setPriceBitget((Double) ticket.get("price"));
                priceAllKey.setPriceBitgetText((String) ticket.get("priceText"));
            }
        }
    }

    public void getTicketsBingx() {
        Status status = checkStatusService("http://bingx-request/health");
        if (!status.equals(Status.UP)) {
            return;
        }
        List<LinkedHashMap> tickets = restTemplate.getForObject("http://bingx-request/tickers", List.class);

        for (LinkedHashMap ticket : tickets) {
            PriceAll priceAllKey = ticketsPriceMap.get(ticket.get("symbol"));
            if (priceAllKey == null) continue;
            priceAllKey.setPriceBingx((Double) ticket.get("price"));
            priceAllKey.setPriceBingxText((String) ticket.get("priceText"));

//            if (priceAllKey == null){
//                PriceAll newPriceAll = new PriceAll();
//                newPriceAll.setSymbol((String) ticket.get("symbol"));
//                newPriceAll.setPriceBybit((Double) ticket.get("price"));
//                priceAllMap.put((String) ticket.get("symbol"), newPriceAll);
            //} else priceAllKey.setPriceBingx((Double) ticket.get("price"));
        }

    }

    public void getTicketsKucoin() {
        Status status = checkStatusService("http://kucoin-request/health");
        if (!status.equals(Status.UP)) {
            return;
        }
        List<LinkedHashMap> tickets = restTemplate.getForObject("http://kucoin-request/tickers", List.class);

        for (LinkedHashMap ticket : tickets) {
            PriceAll priceAllKey = ticketsPriceMap.get(ticket.get("symbol"));
//            if (priceAllKey == null) continue;
//            priceAllKey.setPriceKucoin((Double) ticket.get("price"));
            if (priceAllKey == null) {
                PriceAll newPriceAll = new PriceAll();
                newPriceAll.setSymbol((String) ticket.get("symbol"));
                newPriceAll.setPriceKucoin((Double) ticket.get("price"));
                newPriceAll.setPriceKucoinText((String) ticket.get("priceText"));
                ticketsPriceMap.put((String) ticket.get("symbol"), newPriceAll);
            } else {
                priceAllKey.setPriceKucoin((Double) ticket.get("price"));
                priceAllKey.setPriceKucoinText((String) ticket.get("priceText"));
            }
        }
    }

    public void getTicketsHuobi() {
        Status status = checkStatusService("http://huobi-request/health");
        if (!status.equals(Status.UP)) {
            return;
        }
        List<LinkedHashMap> tickets = restTemplate.getForObject("http://huobi-request/tickers", List.class);

        for (LinkedHashMap ticket : tickets) {
            PriceAll priceAllKey = ticketsPriceMap.get(ticket.get("symbol"));
//            if (priceAllKey == null) continue;
//            priceAllKey.setPriceHuobi((Double) ticket.get("price"));
            if (priceAllKey == null) {
                PriceAll newPriceAll = new PriceAll();
                newPriceAll.setSymbol((String) ticket.get("symbol"));
                newPriceAll.setPriceHuobi((Double) ticket.get("price"));
                newPriceAll.setPriceHuobiText((String) ticket.get("priceText"));
                ticketsPriceMap.put((String) ticket.get("symbol"), newPriceAll);
            } else {
                priceAllKey.setPriceHuobi((Double) ticket.get("price"));
                priceAllKey.setPriceHuobiText((String) ticket.get("priceText"));
            }
        }
    }

    public void getTicketsMexc() {
        Status status = checkStatusService("http://mexc-request/health");
        if (!status.equals(Status.UP)) {
            return;
        }
        List<LinkedHashMap> tickets = restTemplate.getForObject("http://mexc-request/tickers", List.class);

        for (LinkedHashMap ticket : tickets) {
            PriceAll priceAllKey = ticketsPriceMap.get(ticket.get("symbol"));
            if (priceAllKey == null) continue;
            priceAllKey.setPriceMexc((Double) ticket.get("price"));
            priceAllKey.setPriceMexcText((String) ticket.get("priceText"));
//            if (priceAllKey == null) {
//                PriceAll newPriceAll = new PriceAll();
//                newPriceAll.setSymbol((String) ticket.get("symbol"));
//                newPriceAll.setPriceBybit((Double) ticket.get("price"));
//                priceAllMap.put((String) ticket.get("symbol"), newPriceAll);
//            } else priceAllKey.setPriceMexc((Double) ticket.get("price"));
        }
    }

    public void getTicketsCoinW() {
        Status status = checkStatusService("http://coinw-request/health");
        if (!status.equals(Status.UP)) {
            return;
        }
        List<LinkedHashMap> tickets = restTemplate.getForObject("http://coinw-request/tickers", List.class);

        for (LinkedHashMap ticket : tickets) {
            PriceAll priceAllKey = ticketsPriceMap.get(ticket.get("symbol"));
//            if (priceAllKey == null) continue;
//            priceAllKey.setPriceCoinW((Double) ticket.get("price"));
            if (priceAllKey == null) {
                PriceAll newPriceAll = new PriceAll();
                newPriceAll.setSymbol((String) ticket.get("symbol"));
                newPriceAll.setPriceCoinW((Double) ticket.get("price"));
                newPriceAll.setPriceCoinWText((String) ticket.get("priceText"));
                ticketsPriceMap.put((String) ticket.get("symbol"), newPriceAll);
            } else {
                priceAllKey.setPriceCoinW((Double) ticket.get("price"));
                priceAllKey.setPriceCoinWText((String) ticket.get("priceText"));
            }
        }
    }

    public void getTicketsGate() {
        Status status = checkStatusService("http://gate-request/health");
        if (!status.equals(Status.UP)) {
            return;
        }
        List<LinkedHashMap> tickets = restTemplate.getForObject("http://gate-request/tickers", List.class);

        for (LinkedHashMap ticket : tickets) {
            PriceAll priceAllKey = ticketsPriceMap.get(ticket.get("symbol"));
//            if (priceAllKey == null) continue;
//            priceAllKey.setPriceGate((Double) ticket.get("price"));
            if (priceAllKey == null) {
                PriceAll newPriceAll = new PriceAll();
                newPriceAll.setSymbol((String) ticket.get("symbol"));
                newPriceAll.setPriceGate((Double) ticket.get("price"));
                newPriceAll.setPriceGateText((String) ticket.get("priceText"));
                ticketsPriceMap.put((String) ticket.get("symbol"), newPriceAll);
            } else {
                priceAllKey.setPriceGate((Double) ticket.get("price"));
                priceAllKey.setPriceGateText((String) ticket.get("priceText"));
            }
        }
    }

    public void filterPriceAll() {
        HashMap<String, PriceAll> priceAllMapFilter = ticketsPriceMap.entrySet().stream().filter(new Predicate<Map.Entry<String, PriceAll>>() {
            @Override
            public boolean test(Map.Entry<String, PriceAll> stringPriceAllEntry) {
                PriceAll priceAll = stringPriceAllEntry.getValue();
                Double summ = priceAll.getPriceOkx()
                        + priceAll.getPriceBybit()
                        + priceAll.getPriceOkx()
                        + priceAll.getPriceBitget()
                        + priceAll.getPriceBingx()
                        + priceAll.getPriceKucoin()
                        + priceAll.getPriceHuobi()
                        + priceAll.getPriceMexc()
                        + priceAll.getPriceCoinW()
                        + priceAll.getPriceGate();
                return summ.equals(0.0) ? false : true;

            }
        }).collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        ticketsPriceMap = priceAllMapFilter;
    }

    public void sortedTicketPriceMap() {
        HashMap<String, PriceAll> priceAllMapSorted = ticketsPriceMap.entrySet().stream().
                sorted((o1, o2) -> o1.getValue().symbol.compareTo(o2.getValue().symbol)).
                collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        ticketsPriceMap = priceAllMapSorted;
    }

    public void sortedDeltaMap() {
        HashMap<String, Delta> deltaMapSorted = deltaMap.entrySet().stream().
                sorted((o1, o2) -> o1.getValue().symbol.compareTo(o2.getValue().symbol)).
                collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        deltaMap = deltaMapSorted;
    }

    public void replaceValueCoin() {

        for (ReplaceValueCoin replaceValueCoin : new ArrayReplaceValueCoin().getList()) {
            Double valueFrom = 0.0;
            String valueFromText = "";

            //получаем цену монеты которая правильная и зануляем ее
            if (ticketsPriceMap.containsKey(replaceValueCoin.getCoinFrom())) {
                PriceAll entryFrom = ticketsPriceMap.get(replaceValueCoin.getCoinFrom());
                Field fieldValueFrom = null;
                try {
                    fieldValueFrom = entryFrom.getClass().getDeclaredField(replaceValueCoin.getCoinFrom());
                    valueFrom = (Double) fieldValueFrom.get(entryFrom);
                    valueFromText = (String) fieldValueFrom.get(entryFrom);
//                    Field fieldTo = entryTo.getClass().getDeclaredField(replaceValueCoin.getExchange());
                    fieldValueFrom.set(entryFrom, 0.0);
                    entryFrom.refillPriceDouble();
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            //перезаписываем цену манеты на правильную
            if (ticketsPriceMap.containsKey(replaceValueCoin.getCoinTo())) {
                PriceAll entryTo = ticketsPriceMap.get(replaceValueCoin.getCoinTo());
                Field fieldValueTo = null;
                try {
                    Field fieldTo = entryTo.getClass().getDeclaredField(replaceValueCoin.getExchange());
                    fieldTo.set(entryTo, valueFromText);
                    entryTo.refillPriceDouble();
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private Status checkStatusService(String urlHealth) {
        Map<String, String> status = null;
        Status statusCod = Status.OUT_OF_SERVICE;
        try {
            status = (Map<String, String>) restTemplate.getForObject(urlHealth, Object.class);
            String cod = status.get("status");
            statusCod = new Status(cod);
        } catch (RestClientException restClientException) {
            restClientException.printStackTrace();
        } catch (RuntimeException runtimeException) {
            System.out.println(runtimeException.getMessage());
//            runtimeException.printStackTrace();
        }
        if (statusCod != Status.UP) {
            System.out.println(String.format("status: %s, url: %s", statusCod, urlHealth));
        }
        return statusCod;
    }


}