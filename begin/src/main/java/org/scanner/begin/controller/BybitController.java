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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/bybit")
public class BybitController {

    @Autowired
    BybitClient bybitClient;

    @Autowired
    BeginServices beginServices;

    @GetMapping("/form")
    public String index(Model model) {
        beginServices.getTicketsBybit();
        model.addAttribute("delta", bybitClient.getDeltaBybitMap().values());
        return "bybit";
    }

    @GetMapping("/update")
    public String update() {
        return "redirect:/bybit/form";
    }

}
