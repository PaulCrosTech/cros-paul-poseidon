package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.service.ITradeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping(path = "/trade")
public class TradeController {


    private final ITradeService tradeService;

    /**
     * Constructor
     *
     * @param tradeService the Trade service
     */
    public TradeController(ITradeService tradeService) {
        this.tradeService = tradeService;
    }

    /**
     * Add generic attributes to the model
     *
     * @param model the model
     */
    @ModelAttribute
    private void addAttributes(Model model) {
        model.addAttribute("menuActivated", "trade");
    }

    /**
     * Display the Trade list page
     *
     * @param model the model
     * @return the Trade list page
     */
    @RequestMapping("/list")
    public String home(Model model) {
        log.info("====> GET /trade/list <====");

        List<TradeDto> tradeList = tradeService.findAll();
        model.addAttribute("tradeList", tradeList);
        return "trade/list";
    }

    @GetMapping("/add")
    public String addUser(Trade bid) {
        log.info("====> GET /trade/add <====");
        return "trade/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Trade list
        log.info("====> POST /trade/validate <====");
        return "trade/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Trade by Id and to model then show to the form
        log.info("====> GET /trade/update/{} <====", id);
        return "trade/update";
    }

    @PostMapping("/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Trade and return Trade list
        log.info("====> POST /trade/update/{} <====", id);
        return "redirect:/trade/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Trade by Id and delete the Trade, return to Trade list
        log.info("====> GET /trade/delete/{} <====", id);
        return "redirect:/trade/list";
    }
}
