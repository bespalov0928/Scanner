package org.scanner.begin.services;

import org.scanner.begin.component.BybitClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BeginServices {

    @Autowired
    private BybitClient bybitClient;

    @Scheduled(initialDelayString = "${task.initial.delay.millis}", fixedDelayString = "${task.fixed.delay.millis}")
    public void startBybit(){
        System.out.println("startBybit");
        bybitClient.bybitStart();
    }
}
