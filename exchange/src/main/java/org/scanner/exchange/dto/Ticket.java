package org.scanner.exchange.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Ticket {
    String coin1;
    String coin2;
    Double price;
    Date date_request;
}
