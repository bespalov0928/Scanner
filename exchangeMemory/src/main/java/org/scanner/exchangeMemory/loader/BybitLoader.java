package org.scanner.exchangeMemory.loader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.scanner.core.dto.Answ;
import org.scanner.core.dto.Resp;
import org.scanner.core.dto.Ticket;
import org.scanner.core.loader.Loader;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BybitLoader implements Loader {


    @Override
    public void load(Resp respons, Answ answer) {
        List<Ticket> ticketList = parserTickers(answer);
        ticketList = ticketList.stream().filter(ticket -> ticket.getVolume24Usdt() > 1000000).toList();
    }

    private List<Ticket> parserTickers(Answ answer) {
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

    private Ticket parseLine(Resp respons, Answ answer) {
        Ticket ticker = new Ticket();
        ticker.setCoin1(respons.getCoin1());
        ticker.setCoin2(respons.getCoin2());
        ticker.setPrice(answer.getDocument().get("bid1Price").asDouble());
        ticker.setDate_request(answer.getDateRequest());
        return ticker;
    }


    @Override
    public void loadPairs(Resp respons, Answ answer) {
//        String insertQuery = String.format("insert into %s (NAME, VOLUME24USDT) values(?,?)", respons.getName());
        List<Ticket> ticketList = parserPairs(respons, answer);
        ticketList = ticketList.stream().filter(ticket -> ticket.getVolume24Usdt() > 1000000).toList();
    }

    private List<Ticket> parserPairs(Resp respons, Answ answer) {
        List<Ticket> ticketList = new ArrayList<>();
        for (JsonNode jsonNode : answer.getDocuments()) {
            Ticket ticker = new Ticket();
            ticker.setSymbol(jsonNode.get("symbol").asText());
            ticker.setVolume24Usdt(jsonNode.get("turnover24h").asDouble());
            ticketList.add(ticker);
        }
        return ticketList;
    }

    @Override
    public JsonNode document(JsonNode message) {
        var result = message.get("result").get("list").get(0);
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
        return result;
    }
}
