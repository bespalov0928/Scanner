package org.scanner.exchangeOkx.component;

//import org.scanner.exchange.dto.Resp;
import org.scanner.core.dto.Resp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class OkxClient {
    private final RestTemplate restTemplate;
    @Value("${dwh.name}")
    private String name;
    @Value("${dwh.url}")
    private String url;

//    private final DiscoveryClient discoveryClient;

    public OkxClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
//        this.discoveryClient = discoveryClient;
    }

    public void market() throws URISyntaxException {
        URI uri = new URI("http://exchange/tickers");
//        String name = "okx";
//        String url = "https://www.okx.com/api/v5/market/index-tickers?instId=BTC-USDT";
        String coin1 = "BTC";
        String coin2 = "USDT";
        String nameLoader = "org.scanner.exchange.loader.OkxLoader";
        Resp resp = new Resp(name, url, coin1, coin2, nameLoader);

        var rsl = restTemplate.postForLocation(uri, resp);
//        System.out.println("http://exchange/tickers");
//        System.out.println("rsl: " + rsl);
    }
}
