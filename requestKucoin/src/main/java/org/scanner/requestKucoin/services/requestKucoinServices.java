package org.scanner.requestKucoin.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.scanner.core.dto.TicketBasic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class requestKucoinServices {

    @Value("${dwh.url}")
    private String url;

    public List<TicketBasic> getRequest() throws IOException {
        String response = runRequest(this.url);
        JsonNode jsonNode = processing(response);
        List<JsonNode> jsonList = processingList(jsonNode);
        List<TicketBasic> ticketList = parserJsonList(jsonList);
        List<TicketBasic> filterList = filterList(ticketList);
        return filterList;
    }

    public static String runRequest(String url) throws IOException {
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
        ArrayNode list = (ArrayNode) jsonNode.get("data").get("ticker");
        Iterator<JsonNode> iterator = list.elements();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }

    private static List<TicketBasic> parserJsonList(List<JsonNode> jsonList) {
        List<TicketBasic> ticketList = new ArrayList<>();
        for (JsonNode jsonNode : jsonList) {
            TicketBasic ticker = new TicketBasic();
            ticker.setSymbol(jsonNode.get("symbol").asText().replace("-", ""));
            ticker.setPrice(jsonNode.get("last").asDouble());
            ticker.setPriceText(String.format("%.5f", ticker.getPrice()));
            ticker.setVolume24Usdt(jsonNode.get("volValue").asDouble());
            ticketList.add(ticker);
        }
        return ticketList;
    }

    private static List<TicketBasic> filterList(List<TicketBasic> ticketList){
        ticketList = ticketList.stream().filter(ticket -> ticket.getVolume24Usdt() > 1000000).toList();

        List<TicketBasic> list = ticketList.stream().sorted(
                new Comparator<TicketBasic>() {
                    @Override
                    public int compare(TicketBasic o1, TicketBasic o2) {
                        int rsl = o2.getPrice().compareTo(o1.getPrice());
                        return rsl;
                    }
                }).collect(Collectors.toList());
        return list;
    }
}
