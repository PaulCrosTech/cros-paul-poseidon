package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.dto.AlertClass;
import com.nnk.springboot.dto.BidDto;
import com.nnk.springboot.dto.FlashMessage;
import com.nnk.springboot.service.impl.BidListService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


/**
 * The BidList controller
 */
@Controller
@Slf4j
@RequestMapping(path = "/bidList")
public class BidListController {


    private final BidListService bidListService;

    /**
     * Constructor
     *
     * @param bidListService the bidListService
     */
    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    /**
     * Add generic attributes to the model
     *
     * @param model the model
     */
    @ModelAttribute
    private void addAttributes(Model model) {
        model.addAttribute("menuActivated", "bidList");
    }

    /**
     * Display the bid list page
     *
     * @param model the model
     * @return the bid list page
     */
    @RequestMapping("/list")
    public String home(Model model) {
        log.info("====> GET /bidList/list <====");

        List<BidDto> bidLists = bidListService.findAll();
        model.addAttribute("bidLists", bidLists);

        return "bidList/list";
    }

    /**
     * Display the bid add form
     *
     * @param model the model
     * @return the bid add page
     */
    @GetMapping("/add")
    public String addBidForm(Model model) {
        log.info("====> GET /bidList/add <====");
        model.addAttribute("bidDto", new BidDto());
        return "bidList/add";
    }

    /**
     * Validate the bid and save it to the database
     *
     * @param bidDto             the bidDto
     * @param result             the result
     * @param redirectAttributes the redirectAttributes
     * @return the bid add page
     */
    @PostMapping("/validate")
    public String validate(@Valid BidDto bidDto,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        log.info("====> POST /bidList/validate <====");

        if (result.hasErrors()) {
            log.debug("====> POST /bidList/validate : form contains error <====");
            return "bidList/add";
        }

        bidListService.add(bidDto);

        log.info("====> POST /bidList/validate : bid is created <====");
        FlashMessage flashMessage = new FlashMessage(AlertClass.ALERT_SUCCESS, "Bid created successfully");
        redirectAttributes.addFlashAttribute("flashMessage", flashMessage);
        return "redirect:/bidList/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get BidList by Id and to model then show to the form
        log.info("====> GET /bidList/update <====");
        return "bidList/update";
    }

    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid Bid bid,
                            BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update BidList and return list BidList
        log.info("====> POST /bidList/update/{} <====", id);
        return "redirect:/bidList/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find BidList by Id and delete the bid, return to BidList list
        log.info("====> GET /bidList/delete/{} <====", id);
        return "redirect:/bidList/list";
    }
}
