package org.scanner.requestGate.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class CastomHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        Double chance = ThreadLocalRandom.current().nextDouble();
        Health.Builder status = Health.up();
        if (chance > 0.9) {
            status = status.down();
        }
        return status.build();
    }
}
