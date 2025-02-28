package org.scanner.bybit.dto;

import lombok.Data;
import java.sql.Date;

@Data
public class Ticker {
    Date date_request;
    String pair;
    Double price;
}
