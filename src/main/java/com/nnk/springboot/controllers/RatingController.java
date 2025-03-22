package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.service.impl.RatingService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * The Rating controller
 */
@Controller
@Slf4j
@RequestMapping(path = "/rating")
public class RatingController {


    private final RatingService ratingService;

    /**
     * Constructor
     *
     * @param ratingService the ratingService
     */
    public RatingController(RatingService ratingService) {
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
    @RequestMapping("/list")
    public String home(Model model) {
        log.info("====> GET /rating/list <====");

        List<RatingDto> ratingDtoList = ratingService.findAll();
        model.addAttribute("ratingDtoList", ratingDtoList);

        return "rating/list";
    }

    @GetMapping("/add")
    public String addRatingForm(Rating rating) {
        log.info("====> GET /rating/add <====");
        return "rating/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Rating list
        log.info("====> POST /rating/validate <====");
        return "rating/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Rating by Id and to model then show to the form
        log.info("====> GET /rating/update/{} <====", id);
        return "rating/update";
    }

    @PostMapping("/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rating and return Rating list
        log.info("====> POST /rating/update/{} <====", id);
        return "redirect:/rating/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Rating by Id and delete the Rating, return to Rating list
        log.info("====> GET /rating/delete/{} <====", id);
        return "redirect:/rating/list";
    }
}
