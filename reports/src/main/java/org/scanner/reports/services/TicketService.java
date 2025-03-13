package org.scanner.reports.services;


import lombok.AllArgsConstructor;
import org.scanner.reports.dto.Ticket;
import org.scanner.reports.repository.TicketRepositoryImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.scanner.reports.repository.TicketRepository.ROW_MAPPER;

@Service
@AllArgsConstructor
public class TicketService {
//    protected JdbcTemplate jdbcTemplate;
    protected TicketRepositoryImpl ticketRepository;

    public List<Ticket> getTicket() {
        List<Ticket> rsl = new ArrayList<>();
//        var rsl_ = jdbcTemplate.queryForList("SELECT * FROM rep.bybit");
//        var rsl = jdbcTemplate.query("SELECT * FROM rep.bybit", ROW_MAPPER);
        rsl = ticketRepository.findAll();
        return rsl;
    }
}
