package org.scanner.begin.controller;

import lombok.AllArgsConstructor;
import org.scanner.begin.component.BybitClient;
import org.scanner.begin.services.BeginServices;
import org.scanner.core.dto.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@AllArgsConstructor
public class IndexController {

    @Autowired
    BybitClient bybitClient;
    @Autowired
    BeginServices beginServices;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("tickets", bybitClient.getPriceAllMap().values());
        return "index";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Ticket ticket) {
        beginServices.getTickets();
        return "redirect:/index";
    }

    @GetMapping("/process/update")
    public String Process(Model model) {
        beginServices.processingDelta("bybit");
        beginServices.processingDelta("okx");
        beginServices.processingDelta("bitget");
        beginServices.processingDelta("bingx");
        beginServices.processingDelta("kucoin");
        beginServices.processingDelta("huobi");
        beginServices.processingDelta("mexc");
        beginServices.processingDelta("coinw");
        beginServices.processingDelta("gate");
        model.addAttribute("delta", bybitClient.getDeltaMap().values());
        return "process";
    }


    @GetMapping("/bybit/form")
    public String formBybit(Model model) {
        beginServices.processingDelta("bybit");
        model.addAttribute("delta", bybitClient.getDeltaMap().values());
        return "process";
    }

    @GetMapping("/okx/form")
    public String formOkx(Model model) {
        beginServices.processingDelta("okx");
        model.addAttribute("delta", bybitClient.getDeltaMap().values());
        return "process";
    }

    @GetMapping("/bitget/form")
    public String formBitget(Model model) {
        beginServices.processingDelta("bitget");
        model.addAttribute("delta", bybitClient.getDeltaMap().values());
        return "process";
    }

    @GetMapping("/bingx/form")
    public String formBingx(Model model) {
        beginServices.processingDelta("bingx");
        model.addAttribute("delta", bybitClient.getDeltaMap().values());
        return "process";
    }

    @GetMapping("/kucoin/form")
    public String formKucoin(Model model) {
        beginServices.processingDelta("kucoin");
        model.addAttribute("delta", bybitClient.getDeltaMap().values());
        return "process";
    }

    @GetMapping("/huobi/form")
    public String formHuobi(Model model) {
        beginServices.processingDelta("huobi");
        model.addAttribute("delta", bybitClient.getDeltaMap().values());
        return "process";
    }

    @GetMapping("/mexc/form")
    public String formMexc(Model model) {
        beginServices.processingDelta("mexc");
        model.addAttribute("delta", bybitClient.getDeltaMap().values());
        return "process";
    }

    @GetMapping("/coinw/form")
    public String formCoinw(Model model) {
        beginServices.processingDelta("coinw");
        model.addAttribute("delta", bybitClient.getDeltaMap().values());
        return "process";
    }

    @GetMapping("/gate/form")
    public String formGate(Model model) {
        beginServices.processingDelta("gate");
        model.addAttribute("delta", bybitClient.getDeltaMap().values());
        return "process";
    }
}
