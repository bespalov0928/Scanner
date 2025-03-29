package org.scanner.exchange.loader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
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
import java.util.Iterator;
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
        String insertQuery = String.format("insert into %s (coin1, coin2, price, PAIR, DATE_REQUEST) values(?,?,?,?,?)", respons.getName());
        List<Ticket> ticketList = parserTickers(respons, answer);
        ticketList = ticketList.stream().filter(ticket -> ticket.getVolume24Usdt() > 1000000).toList();
        jdbcTemplate.execute(String.format("truncate table REP.%s", respons.getName()));
        insertBatch(insertQuery, ticketList, answer.getDateRequest());

//        String insertQuery = String.format("insert into %s (coin1, coin2, price, DATE_REQUEST) values(?,?,?,?)", respons.getName());
//        List<Ticket> parsedList = new ArrayList<>();
//        Ticket ticket = parseLine(respons, answer);
//        parsedList.add(ticket);
//        insertBatch(insertQuery, parsedList, answer.getDateRequest());
    }

    private Ticket parseLine(Resp respons, Answ answer) {
        Ticket ticker = new Ticket();
        ticker.setCoin1(respons.getCoin1());
        ticker.setCoin2(respons.getCoin2());
        ticker.setPrice(answer.getDocument().get("idxPx").asDouble());
        ticker.setDate_request(answer.getDateRequest());
        return ticker;
    }
    private List<Ticket> parserTickers(Resp respons, Answ answer) {
        List<Ticket> ticketList = new ArrayList<>();
        for (JsonNode jsonNode : answer.getDocuments()) {
            Ticket ticker = new Ticket();
            ticker.setSymbol(jsonNode.get("instId").asText());
            ticker.setPrice(jsonNode.get("last").asDouble());
            ticker.setDate_request(answer.getDateRequest());
            ticker.setVolume24Usdt(jsonNode.get("volCcy24h").asDouble());
            ticketList.add(ticker);
        }
        return ticketList;
    }

    private void insertBatch(String query, List<Ticket> lines, Date dateRequest) {
        jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                String symbol = lines.get(i).getSymbol();
                Util.sv(ps, 1, symbol.split("-")[0]);
                Util.sv(ps, 2, symbol.split("-")[1]);
                Util.sv(ps, 3, lines.get(i).getPrice());
                Util.sv(ps, 4, lines.get(i).getSymbol().replace("-", ""));
                Util.sv(ps, 5, new java.sql.Date(dateRequest.getTime()));
            }

            @Override
            public int getBatchSize() {
                return lines.size();
            }
        });
    }


    @Override
    public void loadPairs(Resp respons, Answ answer) {
        String insertQuery = String.format("insert into %s (NAME, VOLUME24USDT) values(?,?)", respons.getName());
        List<Ticket> ticketList = parserPairs(respons, answer);
        ticketList = ticketList.stream().filter(ticket -> ticket.getVolume24Usdt() > 1000000).toList();
        jdbcTemplate.execute(String.format("truncate table REP.%s", respons.getName()));
        insertBatchPairs(insertQuery, ticketList);
    }

    private List<Ticket> parserPairs(Resp respons, Answ answer) {
        List<Ticket> ticketList = new ArrayList<>();
        for (JsonNode jsonNode : answer.getDocuments()) {
            Ticket ticker = new Ticket();
            ticker.setSymbol(jsonNode.get("instId").asText());
            ticker.setVolume24Usdt(jsonNode.get("volCcy24h").asDouble());
            ticketList.add(ticker);
        }
        return ticketList;
    }

    private void insertBatchPairs(String query, List<Ticket> lines) {
        jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Util.sv(ps, 1, lines.get(i).getSymbol());
                Util.sv(ps, 2, lines.get(i).getVolume24Usdt());
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

    @Override
    public List<JsonNode> documents(JsonNode message) {
        List<JsonNode> result = new ArrayList<>();
        ArrayNode list = (ArrayNode) message.get("data");
        Iterator<JsonNode> iterator = list.elements();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }
}
