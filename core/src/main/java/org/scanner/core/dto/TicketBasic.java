package org.scanner.core.dto;

import lombok.Data;

@Data
public class TicketBasic {
    String symbol;
    Double price = 0.0;
    String priceText = "";
    Double volume24Usdt = 0.0;
}
