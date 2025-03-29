package org.scanner.exchangeBybit.component;

import org.scanner.core.dto.Resp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class Client {
    private final RestTemplate restTemplate;
    @Value("${dwh.name}")
    private String name;
    @Value("${dwh.url}")
    private String url;
    @Value("${dwh.nameLoader}")
    private String nameLoader;
    @Value("${dwh.tablePairs}")
    private String tablePairs;
    @Value("${dwh.urlPairs}")
    private String urlPairs;

    public Client(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void tickers() throws URISyntaxException {
        URI uri = new URI("http://exchange/tickers");
//        String name = "bybit";
//        String url = "https://api.bybit.com/v5/market/tickers?category=spot&symbol=BTCUSDT";
        String coin1 = "";
        String coin2 = "";
//        String nameLoader = "org.scanner.exchange.loader.BybitLoader";
        Resp resp = new Resp(name, url, coin1, coin2, nameLoader);

        var rsl = restTemplate.postForLocation(uri, resp);
//        System.out.println("http://exchange/tickers");
//        System.out.println("rsl: " + rsl);
    }

    public void pairs() throws URISyntaxException {
        URI uri = new URI("http://exchange/pairs");
        String coin1 = "";
        String coin2 = "";
//        String nameLoader = "org.scanner.exchange.loader.BybitLoader";
        Resp resp = new Resp(tablePairs, urlPairs, coin1, coin2, nameLoader);
        var rsl = restTemplate.postForLocation(uri, resp);
    }
}
