package org.scanner.exchange.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.scanner.core.dto.Pairs;
import org.scanner.exchange.dto.Answ;
import org.scanner.exchange.dto.NodeLoader;
import org.scanner.core.dto.Resp;
import org.scanner.exchange.loader.Loader;
import org.scanner.exchange.loader.OkxLoader;
import org.scanner.exchange.loader.BybitLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class ExchangeServices {
    private final JdbcTemplate jdbcTemplate;
    private final Date dateRequest = new Date(new java.util.Date().getTime());

    public ExchangeServices(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @SuppressWarnings("unchecked")
    public static Loader newLoader(final String className, final Object... args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
//        return (T) Class.forName(className).getConstructor(JdbcTemplate.class, NodeLoader.class).newInstance(args);
//        Class<?> loader = Class.forName(className);
        return (Loader) Class.forName(className).getConstructor(JdbcTemplate.class).newInstance(args);
    }

    public void tickers(Resp resp) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String urlBegin = resp.getUrl();
        for (Pairs pair: Pairs.values()){
            String coin1 = pair.toString().split("_")[0];
            String coin2 = pair.toString().split("_")[1];
            String url = urlBegin;
            url = String.format(url, coin1, coin2);
            resp.setUrl(url);
            resp.setCoin1(coin1);
            resp.setCoin2(coin2);
            Answ answer = runRequest(resp);
            JsonNode document = processing(resp, answer.getAnswer());
            answer.setDocument(document);
            loader(resp, answer);
            System.out.println(pair);
        }
    }

    public static Answ runRequest(Resp resp) {
        Date dateRequest_ = new Date(new java.util.Date().getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateRequest_);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        var dateRequest = cal.getTime();

        String url = resp.getUrl();
        Answ answer = new Answ();
        answer.setDateRequest(dateRequest);
        var client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            answer.setAnswer(response.body().string());
            System.out.println("==============begin==============");
            System.out.println(answer);
            System.out.println("==============end==============");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return answer;
    }

    public JsonNode processing(Resp respons, String answer) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        JsonNode document = null;
        String className = respons.getNameLoader();
        Loader loader = newLoader(className, this.jdbcTemplate);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode message = objectMapper.readValue(answer, JsonNode.class);
            document = loader.document(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return  document;
    }

    public void loader(Resp respons, Answ answer) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String className = respons.getNameLoader();
        Loader loader = newLoader(className, this.jdbcTemplate);
        loader.load(respons, answer);
    }
}
