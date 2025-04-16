package org.scanner.exchangeMemory.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.scanner.core.dto.Answ;
import org.scanner.core.dto.Resp;
import org.scanner.core.loader.Loader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class ExchangeMemoryServices {

    @SuppressWarnings("unchecked")
    public static Loader newLoader(final String className, final Object... args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return (Loader) Class.forName(className).getConstructor().newInstance(args);
    }

    public void tickers(Resp resp) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Answ answer = runRequest(resp);
        List<JsonNode> documents = processingList(resp, answer.getAnswer());
        answer.setDocuments(documents);
        loader(resp, answer);
    }

    public static Answ runRequest(Resp resp) {
        String url = resp.getUrl();
        Answ answer = new Answ();
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

    private List<JsonNode> processingList(Resp respons, String answer) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<JsonNode> documents = new ArrayList<>();
        String className = respons.getNameLoader();
        Loader loader = newLoader(className);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode message = objectMapper.readValue(answer, JsonNode.class);
            documents = loader.documents(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return  documents;
    }

    private void loader(Resp respons, Answ answer) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String className = respons.getNameLoader();
        Loader loader = newLoader(className);
        loader.load(respons, answer);
    }
}
