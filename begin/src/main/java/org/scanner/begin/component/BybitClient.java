package org.scanner.begin.component;

import lombok.Getter;
import lombok.Setter;
import org.scanner.core.dto.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
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
    Map<String, Ticket> tickets = new HashMap<String, Ticket>();

    public BybitClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void bybitStart() {
        restTemplate.getForEntity("http://bybit-service/start", String.class);
        restTemplate.getForEntity("http://okx-service/start", String.class);
    }

    public void getTicketsBybit() {
        List<LinkedHashMap> ticketsBybit = restTemplate.getForObject("http://bybit-request/tickers", List.class);

        for (LinkedHashMap ticket : ticketsBybit) {
            Ticket ticketKey = tickets.get(ticket.get("symbol"));
            if (ticketKey == null) {
                Ticket newTicket = new Ticket();
                newTicket.setSymbol((String) ticket.get("symbol"));
                newTicket.setPriceBybit((Double) ticket.get("priceBybit"));
                tickets.put((String) ticket.get("symbol"), newTicket);
            } else ticketKey.setPriceBybit((Double) ticket.get("priceBybit"));
        }
    }

    public void getTicketsOkx() {
        List<LinkedHashMap> ticketsList = restTemplate.getForObject("http://okx-request/tickers", List.class);

        for (LinkedHashMap ticket : ticketsList) {
            Ticket ticketKey = tickets.get(ticket.get("symbol"));
            if (ticketKey == null) {
                Ticket newTicket = new Ticket();
                newTicket.setSymbol((String) ticket.get("symbol"));
                newTicket.setPriceOkx((Double) ticket.get("priceOkx"));
                tickets.put((String) ticket.get("symbol"), newTicket);
            } else ticketKey.setPriceOkx((Double) ticket.get("priceOkx"));
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


        tickets = tickets.entrySet().stream().sorted(new Comparator<Map.Entry<String, Ticket>>() {
            @Override
            public int compare(Map.Entry<String, Ticket> o1, Map.Entry<String, Ticket> o2) {
                return o2.getValue().getDeltaBybitOkx().compareTo(o1.getValue().getDeltaBybitOkx());
            }
        }).collect(Collectors.toMap(
                functionGetKey, //Map.Entry::getKey,
                Map.Entry::getValue,
                (oldValue, newValue) -> oldValue,
                LinkedHashMap::new));
        System.out.println(tickets.size());
    }

    public void filterBybitOkx() {
        tickets = tickets.entrySet().stream().filter(new Predicate<Map.Entry<String, Ticket>>() {
            @Override
            public boolean test(Map.Entry<String, Ticket> stringTicketEntry) {
                return (stringTicketEntry.getValue().getDeltaBybitOkx() <= 0) ? false : true;
            }
        }).collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldValue, newValue) -> oldValue,
                LinkedHashMap::new));
        System.out.println(tickets.size());
    }

}
