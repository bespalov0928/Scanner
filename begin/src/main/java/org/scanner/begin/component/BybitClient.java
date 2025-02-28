package org.scanner.begin.component;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BybitClient {
    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;

    public BybitClient(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    public void bybitStart(){
        restTemplate.getForEntity("http://bybit-service/start", String.class);
    }
}
