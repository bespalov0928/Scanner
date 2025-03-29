package org.scanner.exchange.loader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.NoArgsConstructor;
import org.scanner.core.dto.Resp;
import org.scanner.exchange.dto.Answ;
import org.scanner.exchange.dto.NodeLoader;
import org.scanner.exchange.dto.Ticket;
import org.scanner.exchange.services.Util;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class BybitLoader implements Loader {
    protected JdbcTemplate jdbcTemplate;

    private final static String[] HEAD_FIELDA = {
            "coin1",
            "coin2",
            "price",
            "DATE_REQUEST"
    };

    public BybitLoader(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void load(Resp respons, Answ answer) {
        String insertQuery = String.format("insert into REP.%s (price, PAIR, DATE_REQUEST) values(?,?,?)", respons.getName());
        List<Ticket> ticketList = parserTickers(respons, answer);
        ticketList = ticketList.stream().filter(ticket -> ticket.getVolume24Usdt() > 1000000).toList();
        jdbcTemplate.execute(String.format("truncate table REP.%s", respons.getName()));

        insertBatch(insertQuery, ticketList, answer.getDateRequest());

//        List<Ticket> parsedList = new ArrayList<>();
//        Ticket ticket = parseLine(respons, answer);
//        parsedList.add(ticket);
//        insertBatch(insertQuery, parsedList, answer.getDateRequest());
    }

    private Ticket parseLine(Resp respons, Answ answer) {
        Ticket ticker = new Ticket();
        ticker.setCoin1(respons.getCoin1());
        ticker.setCoin2(respons.getCoin2());
        ticker.setPrice(answer.getDocument().get("bid1Price").asDouble());
        ticker.setDate_request(answer.getDateRequest());
        return ticker;
    }
    private List<Ticket> parserTickers(Resp respons, Answ answer) {
        List<Ticket> ticketList = new ArrayList<>();
        for (JsonNode jsonNode : answer.getDocuments()) {
            Ticket ticker = new Ticket();
            ticker.setSymbol(jsonNode.get("symbol").asText());
            ticker.setPrice(jsonNode.get("bid1Price").asDouble());
            ticker.setDate_request(answer.getDateRequest());
            ticker.setVolume24Usdt(jsonNode.get("turnover24h").asDouble());
            ticketList.add(ticker);
        }
        return ticketList;
    }

    private void insertBatch(String query, List<Ticket> lines, Date dateRequest) {
        jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
//                Util.sv(ps, 1, lines.get(i).getCoin1());
//                Util.sv(ps, 2, lines.get(i).getCoin2());
                Util.sv(ps, 1, lines.get(i).getPrice());
                Util.sv(ps, 2, lines.get(i).getSymbol());
                Util.sv(ps, 3, new java.sql.Date(dateRequest.getTime()));
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
            ticker.setSymbol(jsonNode.get("symbol").asText());
//            ticker.setCoin2(respons.getCoin2());
            ticker.setVolume24Usdt(jsonNode.get("turnover24h").asDouble());
            ticketList.add(ticker);
//            System.out.println(jsonNode);
        }
//        Ticket ticker = new Ticket();
//        ticker.setCoin1(respons.getCoin1());
//        ticker.setCoin2(respons.getCoin2());
//        ticker.set(answer.getDocuments().get("bid1Price").asDouble());
//        ticker.setDate_request(answer.getDateRequest());
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
        var result = message.get("result").get("list").get(0);
//        var rsl = Optional.ofNullable(result).orElse(result.get("category").get("list"));
        return result;
    }

    @Override
    public List<JsonNode> documents(JsonNode message) {
        List<JsonNode> result = new ArrayList<>();
        ArrayNode list = (ArrayNode) message.get("result").get("list");
        Iterator<JsonNode> iterator = list.elements();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
//        List<JsonNode> rsl1 = result..collect(Collectors.toCollection(() -> ))toList();
//        var rsl = Optional.ofNullable(result).orElse(result.get("category").get("list"));
        return result;
    }

}
