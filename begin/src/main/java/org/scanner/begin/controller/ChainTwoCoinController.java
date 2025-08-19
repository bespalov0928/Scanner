package org.scanner.begin.controller;

import lombok.AllArgsConstructor;
import org.scanner.begin.dto.ChainTwoCoinDto;
import org.scanner.begin.services.ChainTwoCoinServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class ChainTwoCoinController {

    @Autowired
    ChainTwoCoinServices services;

    @GetMapping("/chain/update/{spred}")
    public String updateChainTwoCoin(Model model, @PathVariable("spred") String spred) {
//        System.out.println("spred: " + spred);
        ChainTwoCoinDto arrayChain = services.findChainTwoCoin(0.0, "");
        model.addAttribute("arrayChain", arrayChain);
        return "chainTwoCoin";
    }

    @PostMapping("chain/update")
    public String updateChainTwoCoin(Model model, @ModelAttribute("arrayChain") ChainTwoCoinDto arrayChain) {
        System.out.println("arrayChain: " + arrayChain);
        Double spred = arrayChain.getSpred() == null ? 0.0 : arrayChain.getSpred();
        ChainTwoCoinDto arrayChain_ = services.findChainTwoCoin(spred, arrayChain.getСoinPair());
        model.addAttribute("arrayChain", arrayChain_);
        return "chainTwoCoin";
    }
}
