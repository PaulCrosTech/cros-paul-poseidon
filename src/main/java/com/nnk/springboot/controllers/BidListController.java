package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@Slf4j
public class BidListController {
    // TODO: Inject BidList service

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        // TODO: call service find all bids to show to the view
        log.info("====> GET /bidList/list <====");
        model.addAttribute("menuActivated", "bidList");
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bidList) {
        log.info("====> GET /bidList/add <====");
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return bidList list
        log.info("====> POST /bidList/validate <====");
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get BidList by Id and to model then show to the form
        log.info("====> GET /bidList/update <====");
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update BidList and return list BidList
        log.info("====> POST /bidList/update/{} <====", id);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find BidList by Id and delete the bid, return to BidList list
        log.info("====> GET /bidList/delete/{} <====", id);
        return "redirect:/bidList/list";
    }
}
