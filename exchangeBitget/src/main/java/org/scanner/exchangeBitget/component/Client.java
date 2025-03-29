package org.scanner.exchangeBitget.component;

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

    public Client(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void tickers() throws URISyntaxException {
        System.out.println("Tickers");
        URI uri = new URI("http://exchange/tickers");
        Resp resp = new Resp(name, url, "", "", nameLoader);
        var rsl = restTemplate.postForLocation(uri, resp);
    }
}
