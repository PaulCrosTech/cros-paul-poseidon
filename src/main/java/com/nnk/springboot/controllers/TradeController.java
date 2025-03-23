package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.AlertClass;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.dto.FlashMessage;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.exceptions.EntityMissingException;
import com.nnk.springboot.service.ITradeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    /**
     * Display the Trade add form
     *
     * @param model the model
     * @return the Trade add form
     */
    @GetMapping("/add")
    public String addTrade(Model model) {
        log.info("====> GET /trade/add <====");
        model.addAttribute("tradeDto", new TradeDto());
        return "trade/add";
    }

    /**
     * Validate Trade information and save it to the database
     *
     * @param tradeDto           the TradeDto
     * @param result             the result
     * @param redirectAttributes the redirectAttributes
     * @return the Trade list page
     */
    @PostMapping("/validate")
    public String validate(@Valid TradeDto tradeDto,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        log.info("====> POST /trade/validate <====");

        if (result.hasErrors()) {
            log.debug("====> Form contains error <====");
            return "trade/add";
        }

        tradeService.create(tradeDto);

        log.info("====> Trade created successfully <====");
        FlashMessage flashMessage = new FlashMessage(AlertClass.ALERT_SUCCESS, "Trade created successfully");
        redirectAttributes.addFlashAttribute("flashMessage", flashMessage);
        return "redirect:/trade/list";
    }

    /**
     * Display the Trade update form
     *
     * @param id                 the id of the Trade to update
     * @param model              the model
     * @param redirectAttributes the redirectAttributes
     * @return the Trade update page
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        log.info("====> GET /trade/update/{} <====", id);

        try {
            TradeDto tradeDto = tradeService.findById(id);
            model.addAttribute("tradeDto", tradeDto);
        } catch (EntityMissingException e) {
            log.error("====> Error : {} <====", e.getMessage());
            redirectAttributes.addFlashAttribute("flashMessage", new FlashMessage());
            return "redirect:/trade/list";
        }

        return "trade/update";
    }

    /**
     * Update the Trade information
     *
     * @param id                 the id of the Trade to update
     * @param tradeDto           the TradeDto
     * @param result             the result
     * @param redirectAttributes the redirectAttributes
     * @return the Trade list page
     */
    @PostMapping("/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id,
                              @Valid TradeDto tradeDto,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        log.info("====> POST /trade/update/{} <====", id);

        if (result.hasErrors()) {
            return "trade/update";
        }

        String logMessage = "Trade updated successfully";
        FlashMessage flashMessage = new FlashMessage(AlertClass.ALERT_SUCCESS, "Trade updated successfully");

        try {
            tradeService.update(tradeDto);
        } catch (EntityMissingException e) {
            logMessage = e.getMessage();
            flashMessage = new FlashMessage();
        }

        log.info("====> {} <====", logMessage);
        redirectAttributes.addFlashAttribute("flashMessage", flashMessage);

        return "redirect:/trade/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Trade by Id and delete the Trade, return to Trade list
        log.info("====> GET /trade/delete/{} <====", id);
        return "redirect:/trade/list";
    }
}
