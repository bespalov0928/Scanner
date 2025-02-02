package org.scanner.bybit.loader;

import com.fasterxml.jackson.databind.JsonNode;
import org.scanner.bybit.dto.Ticker;
import org.scanner.bybit.services.Util;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketLoader {
    protected JdbcTemplate jdbcTemplate;
    protected List<JsonNode> lines;

    private final static String[] HEAD_FIELDA = {
            "DATE_REQUEST",
            "pair",
            "price"
    };

    public TicketLoader(JdbcTemplate jdbcTemplate, List<JsonNode> lines) {
        this.jdbcTemplate = jdbcTemplate;
        this.lines = lines;
    }

    public void load() {
        String insertQuery = String.format("insert into bybit (DATE_REQUEST, pair, price) values(?,?,?)");
        List<Ticker> parsedList = new ArrayList<>();
        lines.forEach(line -> {
            Ticker parsedLine = parseLine(line);
            parsedList.add(parsedLine);
        });
        insertBatch(insertQuery, parsedList);
    }

    private Ticker parseLine(JsonNode line) {
        Ticker ticker = new Ticker();
        ticker.setPair(line.get("symbol").asText());
        ticker.setPrice(line.get("bid1Price").asDouble());
        return ticker;
    }

    private void insertBatch(String query, List<Ticker> lines){
        jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Util.sv(ps, 1, new Date(new java.util.Date().getTime()));
                Util.sv(ps, 2, lines.get(i).getPair());
                Util.sv(ps, 3, lines.get(i).getPrice());
            }

            @Override
            public int getBatchSize() {
                return lines.size();
            }
        });
    }

}
