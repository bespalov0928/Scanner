package org.scanner.reports.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    private String Pair;
    private Date DateRequest;
    private Double Price;
}
