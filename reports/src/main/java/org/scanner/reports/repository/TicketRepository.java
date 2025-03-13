package org.scanner.reports.repository;

import org.scanner.reports.dto.Ticket;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.List;

public interface TicketRepository {
    RowMapper<Ticket> ROW_MAPPER = (ResultSet resulSet, int rowNum) ->{
//        while (resulSet.next()){
//            System.out.println();
//        }

        Ticket ticket = new Ticket();
//        ticket.setDateRequest(resulSet.getDate("date_request"));
        ticket.setPair(resulSet.getString("pair"));
//        System.out.println(resulSet.getObject(1));
        String price = resulSet.getString("price");
        String priceFormat = String.format("%s.%s", price.split(",")[0], price.split(",")[1]);
        Double PriceDouble = Double.parseDouble(priceFormat);
//        System.out.println(PriceDouble);
        ticket.setPrice(PriceDouble);
//        System.out.println(resulSet.getDate("date_request"));
        ticket.setDateRequest(resulSet.getDate("date_request"));
    return ticket;
    };

    List<Ticket> findAll();
}
