package org.scanner.bybit.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

//@AllArgsConstructor
@Data
public class Ticker {
    Date date_request;
    String pair;
    Double price;
}
