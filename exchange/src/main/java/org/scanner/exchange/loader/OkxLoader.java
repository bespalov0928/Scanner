package org.scanner.exchange.loader;

import com.fasterxml.jackson.databind.JsonNode;
import org.scanner.core.dto.Resp;
import org.scanner.exchange.dto.Answ;
import org.scanner.exchange.dto.Ticket;
import org.scanner.exchange.services.Util;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OkxLoader implements Loader {
    protected JdbcTemplate jdbcTemplate;

    private final static String[] HEAD_FIELDA = {
            "coin1",
            "coin2",
            "price",
            "DATE_REQUEST"
    };

    public OkxLoader(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void load(Resp respons, Answ answer) {
        String insertQuery = String.format("insert into %s (coin1, coin2, price, DATE_REQUEST) values(?,?,?,?)", respons.getName());
        List<Ticket> parsedList = new ArrayList<>();
        Ticket ticket = parseLine(respons, answer);
        parsedList.add(ticket);
        insertBatch(insertQuery, parsedList, answer.getDateRequest());
    }

    private Ticket parseLine(Resp respons, Answ answer) {
        Ticket ticker = new Ticket();
        ticker.setCoin1(respons.getCoin1());
        ticker.setCoin2(respons.getCoin2());
        ticker.setPrice(answer.getDocument().get("idxPx").asDouble());
        ticker.setDate_request(answer.getDateRequest());
        return ticker;
    }

    private void insertBatch(String query, List<Ticket> lines, Date dateRequest) {
        jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Util.sv(ps, 1, lines.get(i).getCoin1());
                Util.sv(ps, 2, lines.get(i).getCoin2());
                Util.sv(ps, 3, lines.get(i).getPrice());
                Util.sv(ps, 4, new java.sql.Date(dateRequest.getTime()));
            }

            @Override
            public int getBatchSize() {
                return lines.size();
            }
        });
    }

    @Override
    public JsonNode document(JsonNode message) {
        var result = message.get("data").get(0);
        return result;
    }
}
