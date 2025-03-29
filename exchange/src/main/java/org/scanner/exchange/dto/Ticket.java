package org.scanner.exchange.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Ticket {
    String coin1;
    String coin2;
    String symbol;
    Double price;
    Double volume24Usdt;
    Date date_request;
}
