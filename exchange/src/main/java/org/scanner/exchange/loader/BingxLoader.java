package org.scanner.exchange.loader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.scanner.core.dto.Resp;
import org.scanner.exchange.dto.Answ;
import org.scanner.exchange.dto.Ticket;
import org.scanner.exchange.services.Util;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BingxLoader implements Loader {
    protected JdbcTemplate jdbcTemplate;

    public BingxLoader(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void load(Resp respons, Answ answer) {
        String insertQuery = String.format("insert into REP.%s (COIN1, COIN2, price, PAIR, DATE_REQUEST) values(?,?,?,?,?)", respons.getName());
        List<Ticket> ticketList = parserTickers(respons, answer);
//        ticketList = ticketList.stream().filter(ticket -> ticket.getVolume24Usdt() > 1000000).toList();
        jdbcTemplate.execute(String.format("truncate table REP.%s", respons.getName()));
        insertBatch(insertQuery, ticketList, answer.getDateRequest());
    }

    private List<Ticket> parserTickers(Resp respons, Answ answer) {
        List<Ticket> ticketList = new ArrayList<>();
        for (JsonNode jsonNode : answer.getDocuments()) {
            ArrayNode tradesList = (ArrayNode) jsonNode.get("trades");
            String symbol = jsonNode.get("symbol").asText();
            Ticket ticker = new Ticket();
            ticker.setCoin1(symbol.split("_")[0]);
            ticker.setCoin2(symbol.split("_")[1]);
            ticker.setSymbol(symbol.replace("_", ""));
            ticker.setPrice(tradesList.get(0).get("price").asDouble());
            ticker.setDate_request(answer.getDateRequest());
//            ticker.setVolume24Usdt(jsonNode.get("usdtVolume").asDouble());
            ticketList.add(ticker);
        }
        return ticketList;
    }

    private void insertBatch(String query, List<Ticket> lines, Date dateRequest) {
        jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Util.sv(ps, 1, lines.get(i).getCoin1());
                Util.sv(ps, 2, lines.get(i).getCoin2());
                Util.sv(ps, 3, lines.get(i).getPrice());
                Util.sv(ps, 4, lines.get(i).getSymbol());
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
    }



    @Override
    public JsonNode document(JsonNode message) {
        var result = message.get("result").get("list").get(0);
        return result;
    }

    @Override
    public List<JsonNode> documents(JsonNode message) {
        List<JsonNode> result = new ArrayList<>();
        ArrayNode listData = (ArrayNode) message.get("data");
        Iterator<JsonNode> iterator = listData.elements();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }
}
