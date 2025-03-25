package org.scanner.begin.component;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BybitClient {
    private final RestTemplate restTemplate;

    public BybitClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void bybitStart(){
       restTemplate.getForEntity("http://bybit-service/start", String.class);
       restTemplate.getForEntity("http://okx-service/start", String.class);
    }
}
