package org.scanner.requestBybit.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.scanner.core.dto.Ticket;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class requestBybitServices {

    public List<Ticket> getRequest() throws IOException {
        String response = runRequest();
        JsonNode jsonNode = processing(response);
        List<JsonNode> jsonList = processingList(jsonNode);
        List<Ticket> ticketList = parserJsonList(jsonList);
        List<Ticket> filterList = filterList(ticketList);
        return filterList;
    }

    public static String runRequest() throws IOException {
        String url = "https://api.bybit.com/v5/market/tickers?category=spot";
        String rsl = "";
        Response response = null;
        var client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder().url(url).build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        rsl = response.body().string();
        return rsl;
    }

    private static JsonNode processing(String answer) {
        JsonNode message = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            message = objectMapper.readValue(answer, JsonNode.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return message;
    }

    private static List<JsonNode> processingList(JsonNode jsonNode) {
        List<JsonNode> result = new ArrayList<>();
        ArrayNode list = (ArrayNode) jsonNode.get("result").get("list");
        Iterator<JsonNode> iterator = list.elements();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }

    private static List<Ticket> parserJsonList(List<JsonNode> jsonList) {
        List<Ticket> ticketList = new ArrayList<>();
        for (JsonNode jsonNode : jsonList) {
            Ticket ticker = new Ticket();
            ticker.setSymbol(jsonNode.get("symbol").asText());
            ticker.setPriceBybit(jsonNode.get("bid1Price").asDouble());
            ticker.setVolume24Usdt(jsonNode.get("turnover24h").asDouble());
            ticketList.add(ticker);
        }
        return ticketList;
    }

    private static List<Ticket> filterList(List<Ticket> ticketList){
        ticketList = ticketList.stream().filter(ticket -> ticket.getVolume24Usdt() > 1000000).toList();

        List<Ticket> list = ticketList.stream().sorted(
                new Comparator<Ticket>() {
                    @Override
                    public int compare(Ticket o1, Ticket o2) {
                        int rsl = o2.getPriceBybit().compareTo(o1.getPriceBybit());
                        return rsl;
                    }
                }).collect(Collectors.toList());
        return list;
    }

}
