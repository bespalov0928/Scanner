package org.scanner.begin.controller;

import lombok.AllArgsConstructor;
import org.scanner.begin.component.RestClient;
import org.scanner.begin.dto.DeltaDto;
import org.scanner.begin.services.GeneralServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@AllArgsConstructor
public class PriceDeltaController {
    @Autowired
    RestClient restClient;

    @Autowired
    GeneralServices generalServices;

    @PostMapping("/priceDelta/update")
    public String update(Model model, @RequestParam("exchange") String exchange) {
//        String exchange = model.getAttribute("exchange").toString();
        generalServices.processingDelta(exchange);
//        generalServices.processingDelta("bybit");
//        generalServices.processingDelta("okx");
//        generalServices.processingDelta("bitget");
//        generalServices.processingDelta("bingx");
//        generalServices.processingDelta("kucoin");
//        generalServices.processingDelta("huobi");
//        generalServices.processingDelta("mexc");
//        generalServices.processingDelta("coinw");
//        generalServices.processingDelta("gate");
        DeltaDto deltaDto= new DeltaDto();
        deltaDto.setExchange(exchange);
        deltaDto.setListDelta(new ArrayList<>(restClient.getDeltaMap().values()));
        model.addAttribute("deltaDto", deltaDto);
        return "priceDelta";
    }

}
