package org.scanner.begin.config;

import org.scanner.core.dto.Ticket;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TicketsConfig {
    @Bean
    Map<String, Ticket> tickets() {
        return new HashMap<String, Ticket>();
    }
}
