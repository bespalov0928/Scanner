package org.scanner.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
//@AllArgsConstructor
//@RequiredArgsConstructor(staticName = "of")
public class Ticket {
    String coin1;
    String coin2;
    String symbol;
    Double priceBybit = 0.0;
    Double priceOkx = 0.0;
    Double DeltaBybitOkx = 0.0;
    Double volume24Usdt = 0.0;
    Date date_request;
}
