package org.scanner.begin.component;

import lombok.Getter;
import lombok.Setter;
import org.scanner.begin.dto.*;
import org.scanner.core.dto.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
@Component
public class BybitClient {
    private final RestTemplate restTemplate;
    //    @Autowired
    Map<String, Ticket> ticketsMap = new HashMap<>();
    HashMap<String, PriceAll> priceAllMap = new HashMap<>();
    HashMap<String, Delta> deltaMap = new HashMap<>();

    public BybitClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void getTicketsBybit() {
        Status status = checkStatusService("http://bybit-request/health");
        if (!status.equals(Status.UP)){
            return;
        }
        List<LinkedHashMap> tickets = restTemplate.getForObject("http://bybit-request/tickers", List.class);

        for (LinkedHashMap ticket : tickets) {
            PriceAll priceAllKey = priceAllMap.get(ticket.get("symbol"));
            if (priceAllKey == null) {
                PriceAll newPriceAll = new PriceAll();
                newPriceAll.setSymbol((String) ticket.get("symbol"));
                newPriceAll.setPriceBybit((Double) ticket.get("price"));
                priceAllMap.put((String) ticket.get("symbol"), newPriceAll);
            } else priceAllKey.setPriceBybit((Double) ticket.get("price"));
        }
    }

    public void getTicketsOkx() {
        Status status = checkStatusService("http://okx-request/health");
        if (!status.equals(Status.UP)){
            return;
        }
        List<LinkedHashMap> tickets = restTemplate.getForObject("http://okx-request/tickers", List.class);

        for (LinkedHashMap ticket : tickets) {
            PriceAll priceAllKey = priceAllMap.get(ticket.get("symbol"));
            if (priceAllKey == null) {
                PriceAll newPriceAll = new PriceAll();
                newPriceAll.setSymbol((String) ticket.get("symbol"));
                newPriceAll.setPriceOkx((Double) ticket.get("price"));
                priceAllMap.put((String) ticket.get("symbol"), newPriceAll);
            } else priceAllKey.setPriceOkx((Double) ticket.get("price"));
        }
    }

    public void getTicketsBitget(){
        Status status = checkStatusService("http://bitget-request/health");
        if (!status.equals(Status.UP)){
            return;
        }
        List<LinkedHashMap> tickets = restTemplate.getForObject("http://bitget-request/tickers", List.class);

        for (LinkedHashMap ticket : tickets) {
            PriceAll priceAllKey = priceAllMap.get(ticket.get("symbol"));
            if (priceAllKey == null) {
                PriceAll newPriceAll = new PriceAll();
                newPriceAll.setSymbol((String) ticket.get("symbol"));
                newPriceAll.setPriceOkx((Double) ticket.get("price"));
                priceAllMap.put((String) ticket.get("symbol"), newPriceAll);
            } else priceAllKey.setPriceBitget((Double) ticket.get("price"));
        }
    }

    public void getTicketsBingx() {
        Status status = checkStatusService("http://bingx-request/health");
        if (!status.equals(Status.UP)){
            return;
        }
        List<LinkedHashMap> tickets = restTemplate.getForObject("http://bingx-request/tickers", List.class);

        for (LinkedHashMap ticket : tickets) {
            PriceAll priceAllKey = priceAllMap.get(ticket.get("symbol"));
            if (priceAllKey == null) continue;
            priceAllKey.setPriceBingx((Double) ticket.get("price"));
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
        if (!status.equals(Status.UP)){
            return;
        }
        List<LinkedHashMap> tickets = restTemplate.getForObject("http://kucoin-request/tickers", List.class);

        for (LinkedHashMap ticket : tickets) {
            PriceAll priceAllKey = priceAllMap.get(ticket.get("symbol"));
//            if (priceAllKey == null) continue;
//            priceAllKey.setPriceKucoin((Double) ticket.get("price"));
            if (priceAllKey == null) {
                PriceAll newPriceAll = new PriceAll();
                newPriceAll.setSymbol((String) ticket.get("symbol"));
                newPriceAll.setPriceBybit((Double) ticket.get("price"));
                priceAllMap.put((String) ticket.get("symbol"), newPriceAll);
            } else priceAllKey.setPriceKucoin((Double) ticket.get("price"));
        }
    }

    public void getTicketsHuobi() {
        Status status = checkStatusService("http://huobi-request/health");
        if (!status.equals(Status.UP)){
            return;
        }
        List<LinkedHashMap> tickets = restTemplate.getForObject("http://huobi-request/tickers", List.class);

        for (LinkedHashMap ticket : tickets) {
            PriceAll priceAllKey = priceAllMap.get(ticket.get("symbol"));
//            if (priceAllKey == null) continue;
//            priceAllKey.setPriceHuobi((Double) ticket.get("price"));
            if (priceAllKey == null) {
                PriceAll newPriceAll = new PriceAll();
                newPriceAll.setSymbol((String) ticket.get("symbol"));
                newPriceAll.setPriceBybit((Double) ticket.get("price"));
                priceAllMap.put((String) ticket.get("symbol"), newPriceAll);
            } else priceAllKey.setPriceHuobi((Double) ticket.get("price"));
        }
    }

    public void getTicketsMexc() {
        Status status = checkStatusService("http://mexc-request/health");
        if (!status.equals(Status.UP)){
            return;
        }
        List<LinkedHashMap> tickets = restTemplate.getForObject("http://mexc-request/tickers", List.class);

        for (LinkedHashMap ticket : tickets) {
            PriceAll priceAllKey = priceAllMap.get(ticket.get("symbol"));
            if (priceAllKey == null) continue;
            priceAllKey.setPriceMexc((Double) ticket.get("price"));
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
        if (!status.equals(Status.UP)){
            return;
        }
        List<LinkedHashMap> tickets = restTemplate.getForObject("http://coinw-request/tickers", List.class);

        for (LinkedHashMap ticket : tickets) {
            PriceAll priceAllKey = priceAllMap.get(ticket.get("symbol"));
//            if (priceAllKey == null) continue;
//            priceAllKey.setPriceCoinW((Double) ticket.get("price"));
            if (priceAllKey == null) {
                PriceAll newPriceAll = new PriceAll();
                newPriceAll.setSymbol((String) ticket.get("symbol"));
                newPriceAll.setPriceBybit((Double) ticket.get("price"));
                priceAllMap.put((String) ticket.get("symbol"), newPriceAll);
            } else priceAllKey.setPriceCoinW((Double) ticket.get("price"));
        }
    }

    public void getTicketsGate() {
        Status status = checkStatusService("http://gate-request/health");
        if (!status.equals(Status.UP)){
            return;
        }
        List<LinkedHashMap> tickets = restTemplate.getForObject("http://gate-request/tickers", List.class);

        for (LinkedHashMap ticket : tickets) {
            PriceAll priceAllKey = priceAllMap.get(ticket.get("symbol"));
//            if (priceAllKey == null) continue;
//            priceAllKey.setPriceGate((Double) ticket.get("price"));
            if (priceAllKey == null) {
                PriceAll newPriceAll = new PriceAll();
                newPriceAll.setSymbol((String) ticket.get("symbol"));
                newPriceAll.setPriceBybit((Double) ticket.get("price"));
                priceAllMap.put((String) ticket.get("symbol"), newPriceAll);
            } else priceAllKey.setPriceGate((Double) ticket.get("price"));
        }
    }

    public void sortedTickets() {

        Function<Map.Entry<String, Ticket>, String> functionGetKey = new Function<Map.Entry<String, Ticket>, String>() {
            @Override
            public String apply(Map.Entry<String, Ticket> stringTicketEntry) {
                stringTicketEntry.getKey();
                return stringTicketEntry.getKey();
            }
        };

        ticketsMap = ticketsMap.entrySet().stream().sorted(new Comparator<Map.Entry<String, Ticket>>() {
            @Override
            public int compare(Map.Entry<String, Ticket> o1, Map.Entry<String, Ticket> o2) {
                return o2.getValue().getDeltaBybitOkx().compareTo(o1.getValue().getDeltaBybitOkx());
            }
        }).collect(Collectors.toMap(
                functionGetKey, //Map.Entry::getKey,
                Map.Entry::getValue,
                (oldValue, newValue) -> oldValue,
                LinkedHashMap::new));
        System.out.println(ticketsMap.size());
    }

    public void filterBybitOkx() {
        ticketsMap = ticketsMap.entrySet().stream().filter(new Predicate<Map.Entry<String, Ticket>>() {
            @Override
            public boolean test(Map.Entry<String, Ticket> stringTicketEntry) {
                return (stringTicketEntry.getValue().getDeltaBybitOkx() <= 0) ? false : true;
            }
        }).collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldValue, newValue) -> oldValue,
                LinkedHashMap::new));
        System.out.println(ticketsMap.size());
    }

    public Status checkStatusService(String urlHealth){
        Map<String, String> status = null;
        Status statusCod = Status.OUT_OF_SERVICE;
        try {
            status = (Map<String, String>) restTemplate.getForObject(urlHealth, Object.class);
            String cod =  status.get("status");
            statusCod = new Status(cod);
        }catch (RestClientException restClientException){
            restClientException.printStackTrace();
        } catch (RuntimeException runtimeException) {
            System.out.println(runtimeException.getMessage());
//            runtimeException.printStackTrace();
        }
        if (statusCod != Status.UP){
            System.out.println(String.format("status: %s, url: %s", statusCod, urlHealth));
        }
        return statusCod;
    }

}
