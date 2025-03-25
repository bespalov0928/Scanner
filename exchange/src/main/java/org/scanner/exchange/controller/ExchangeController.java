package org.scanner.exchange.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.scanner.core.dto.Resp;
import org.scanner.exchange.dto.Answ;
import org.scanner.exchange.dto.NodeLoader;
import org.scanner.exchange.services.ExchangeServices;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;


@AllArgsConstructor
@RestController
public class ExchangeController {

    private final ExchangeServices services;

    @GetMapping("/bybit")
    public String byBit() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String name = "bybit";
        String url = "https://api.bybit.com/v5/market/tickers?category=spot&symbol=BTCUSDT";
        String coin1 = "BTC";
        String coin2 = "USDT";
        String nameLoader = "BybitLoader";
        Resp resp = new Resp(name, url, coin1, coin2, nameLoader);
//        services.exchangeRunV2(resp);
        return "byBit";
    }

    @GetMapping("/okx")
    public String okx() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String name = "okx";
        String url = "https://www.okx.com/api/v5/market/index-tickers?instId=BTC-USDT";
        String coin1 = "BTC";
        String coin2 = "USDT";
        String nameLoader = "OkxLoader";
        Resp resp = new Resp(name, url, coin1, coin2, nameLoader);
//        services.exchangeRunV2(resp);
        return "okx";
    }

    @PostMapping("/tickers")
    public String tickers(@RequestBody Resp resp) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        services.tickers(resp);
//        Answ answer = ExchangeServices.runRequest(resp);
//        JsonNode document = services.processing(resp, answer.getAnswer());
//        answer.setDocument(document);
//        services.loader(resp, answer);
        return "tickers";
    }
}
