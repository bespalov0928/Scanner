package org.scanner.reports.repository;

import lombok.AllArgsConstructor;
import org.scanner.reports.dto.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.scanner.reports.repository.TicketRepository.ROW_MAPPER;

@Component
@AllArgsConstructor
public final class TicketRepositoryImpl {

    private final JdbcTemplate jdbcTemplate;

//    @Override
    public List<Ticket> findAll() {
        List<Ticket> rsl = new ArrayList<>();
        rsl = jdbcTemplate.query("SELECT * FROM rep.bybit", ROW_MAPPER);
//        var rsl_ = jdbcTemplate.queryForList("SELECT * FROM rep.bybit");
        return rsl;
    }
}
