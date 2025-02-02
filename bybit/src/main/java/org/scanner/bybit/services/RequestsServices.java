package org.scanner.bybit.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.scanner.bybit.loader.TicketLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import okhttp3.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@EnableScheduling
public class RequestsServices {
    private final JdbcTemplate jdbcTemplate;

    public RequestsServices(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Scheduled(fixedDelay = 10000)// Отправлять запрос каждые 10 секунд
    public void runBybit() {
        var client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("https://api.bybit.com/v5/market/tickers?category=spot&symbol=BTCUSDT")
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println("==============response==============");
            String answer = response.body().string();
            System.out.println(answer);
//            log.error(answer);
//            log.warn(answer);
            log.info(answer);
//            log.debug(answer);
//            log.trace(answer);
            System.out.println("==============response==============");

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode message = objectMapper.readValue(answer, JsonNode.class);
            JsonNode documents = Util.document(message);
            System.out.println(message.toString());
            List<JsonNode> lines = new ArrayList<>();
            documents.forEach(doc -> {
                lines.add(doc);
            });
            TicketLoader ticketLoader = new TicketLoader(this.jdbcTemplate, lines);
            ticketLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
