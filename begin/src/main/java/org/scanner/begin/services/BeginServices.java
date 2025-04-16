package org.scanner.begin.services;

import org.scanner.begin.component.BybitClient;
import org.scanner.core.dto.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    @Scheduled(initialDelayString = "${task.initial.delay.millis}", fixedDelayString = "${task.fixed.delay.millis}")
    public void getTickets() {
        bybitClient.getTicketsBybit();
        bybitClient.getTicketsOkx();
        processingDeltaBybitOkx();
        bybitClient.sortedTickets();
        bybitClient.filterBybitOkx();
    }

    private void processingDeltaBybitOkx(){
        Map<String, Ticket> mapTickets = bybitClient.getTickets();
        for (String key : mapTickets.keySet()) {
            Ticket ticket = mapTickets.get(key);
            if (ticket.getPriceBybit()==0 || ticket.getPriceOkx()==0) continue;
            ticket.setDeltaBybitOkx(ticket.getPriceBybit()/ ticket.getPriceOkx());
        }

    }

}
