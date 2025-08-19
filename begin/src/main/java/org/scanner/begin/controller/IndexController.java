package org.scanner.begin.controller;

import lombok.AllArgsConstructor;
import org.scanner.begin.component.RestClient;
import org.scanner.begin.dto.DeltaDto;
import org.scanner.begin.dto.IndexDto;
import org.scanner.begin.services.IndexServices;
import org.scanner.begin.services.GeneralServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;


@Controller
@AllArgsConstructor
public class IndexController {

    @Autowired
    RestClient restClient;

    @Autowired
    IndexServices indexServices;

    @Autowired
    GeneralServices generalServices;

    @GetMapping("/index")
    public String index(Model model) {
        IndexDto indexDto = new IndexDto();
        indexDto.setСoinPair("");
        indexDto.setListPrice(new ArrayList<>(restClient.getTicketsPriceMap().values()));
        model.addAttribute("indexDto", indexDto);
        return "index";
    }

    @GetMapping("/update/{сoinPair}")
//    public String update(@ModelAttribute Ticket ticket) {
    public String update(@PathVariable("сoinPair") String сoinPair) {
        indexServices.getTickets(сoinPair);
        return "redirect:/index";
    }

    @PostMapping("/update")
//    public String update(@ModelAttribute Ticket ticket) {
    public String updatePost(Model model, @ModelAttribute IndexDto indexDto) {
        indexServices.getTickets(indexDto.getСoinPair());
        IndexDto indexDtoNew = new IndexDto();
        indexDtoNew.setСoinPair(indexDto.getСoinPair());
        indexDtoNew.setListPrice(new ArrayList<>(restClient.getTicketsPriceMap().values()));
        model.addAttribute("indexDto", indexDtoNew);
//        return "redirect:/index";
        return "index";
    }

    @GetMapping("/chainTwoCoin")
    public String chainTwoCoin() {
        return "redirect:/chain/update/0";
    }

    @GetMapping("/form/{exchange}")
    public String formExchange(Model model, @PathVariable("exchange") String exchange) {
        generalServices.processingDelta(exchange);
        DeltaDto deltaDto = new DeltaDto();
        deltaDto.setExchange(exchange);
        deltaDto.setListDelta(new ArrayList<>(restClient.getDeltaMap().values()));
        model.addAttribute("deltaDto", deltaDto);
        return "priceDelta";
    }
}
