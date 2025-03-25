package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.AlertClass;
import com.nnk.springboot.dto.FlashMessage;
import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.exceptions.EntityMissingException;
import com.nnk.springboot.service.IRatingService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


/**
 * The Rating controller
 */
@Controller
@Slf4j
@RequestMapping(path = "/rating")
public class RatingController {


    private final IRatingService ratingService;

    /**
     * Constructor
     *
     * @param ratingService the ratingService
     */
    public RatingController(IRatingService ratingService) {
        this.ratingService = ratingService;
    }

    /**
     * Add generic attributes to the model
     *
     * @param model the model
     */
    @ModelAttribute
    private void addAttributes(Model model) {
        model.addAttribute("menuActivated", "rating");
    }

    /**
     * Display the Rating list page
     *
     * @param model the model
     * @return the Rating list page
     */
    @GetMapping("/list")
    public String home(Model model) {
        log.info("====> GET /rating/list <====");

        List<RatingDto> ratingDtoList = ratingService.findAll();
        model.addAttribute("ratingDtoList", ratingDtoList);

        return "rating/list";
    }

    /**
     * Display the Rating add form
     *
     * @param model the model
     * @return the Rating add form
     */
    @GetMapping("/add")
    public String addRatingForm(Model model) {
        log.info("====> GET /rating/add <====");
        model.addAttribute("ratingDto", new RatingDto());
        return "rating/add";
    }

    /**
     * Validate the Rating form
     *
     * @param ratingDto          the ratingDto
     * @param result             the result
     * @param redirectAttributes the redirectAttributes
     * @return the Rating add form
     */
    @PostMapping("/validate")
    public String validate(@Valid RatingDto ratingDto,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {

        log.info("====> POST /rating/validate <====");

        if (result.hasErrors()) {
            log.debug("====> Form contains error <====");
            return "rating/add";
        }

        ratingService.create(ratingDto);

        log.info("====> Rating created successfully <====");
        FlashMessage flashMessage = new FlashMessage(AlertClass.ALERT_SUCCESS, "Rating created successfully");
        redirectAttributes.addFlashAttribute("flashMessage", flashMessage);

        return "redirect:/rating/list";
    }

    /**
     * Display the Rating update form
     *
     * @param id                 the id of the rating to update
     * @param model              the model
     * @param redirectAttributes the redirectAttributes
     * @return the Rating update page
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        log.info("====> GET /rating/update/{} <====", id);

        try {
            RatingDto ratingDto = ratingService.findById(id);
            model.addAttribute("ratingDto", ratingDto);
        } catch (EntityMissingException e) {
            log.error("====> Error : {} <====", e.getMessage());
            redirectAttributes.addFlashAttribute("flashMessage", new FlashMessage());
            return "redirect:/rating/list";
        }

        return "rating/update";
    }

    /**
     * Update the Rating
     *
     * @param id                 the id of the rating to update
     * @param ratingDto          the ratingDto
     * @param result             the result
     * @param redirectAttributes the redirectAttributes
     * @return the Rating list page
     */
    @PostMapping("/update/{id}")
    public String updateRating(@PathVariable("id") Integer id,
                               @Valid RatingDto ratingDto,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {

        log.info("====> POST /rating/update/{} <====", id);

        if (result.hasErrors()) {
            return "rating/update";
        }

        String logMessage = "Rating updated successfully";
        FlashMessage flashMessage = new FlashMessage(AlertClass.ALERT_SUCCESS, "Rating updated successfully");

        try {
            ratingService.update(ratingDto);
        } catch (EntityMissingException e) {
            logMessage = e.getMessage();
            flashMessage = new FlashMessage();
        }

        log.info("====> {} <====", logMessage);
        redirectAttributes.addFlashAttribute("flashMessage", flashMessage);
        return "redirect:/rating/list";
    }

    /**
     * Delete the Rating
     *
     * @param id                 the id of the rating to delete
     * @param redirectAttributes the redirectAttributes
     * @return the Rating list page
     */
    @GetMapping("/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id,
                               RedirectAttributes redirectAttributes) {
        log.info("====> GET /rating/delete/{} <====", id);

        String logMessage = "Rating deleted successfully";
        FlashMessage flashMessage = new FlashMessage(AlertClass.ALERT_SUCCESS, "Rating deleted successfully");

        try {
            ratingService.delete(id);
        } catch (EntityMissingException e) {
            logMessage = e.getMessage();
            flashMessage = new FlashMessage();
        }

        log.info("====> {} <====", logMessage);
        redirectAttributes.addFlashAttribute("flashMessage", flashMessage);

        return "redirect:/rating/list";
    }
}
