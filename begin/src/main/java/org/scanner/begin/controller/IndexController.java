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
        model.addAttribute("tickets", bybitClient.getTickets().values());
        return "index";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Ticket ticket) {
        beginServices.getTickets();
        return "redirect:/index";
    }
}
