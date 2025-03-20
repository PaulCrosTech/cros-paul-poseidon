package com.nnk.springboot.controllers;

import com.nnk.springboot.entity.Rating;
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
public class RatingController {
    // TODO: Inject Rating service

    @RequestMapping("/rating/list")
    public String home(Model model) {
        // TODO: find all Rating, add to model
        log.info("====> GET /rating/list <====");
        model.addAttribute("menuActivated", "rating");
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        log.info("====> GET /rating/add <====");
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Rating list
        log.info("====> POST /rating/validate <====");
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Rating by Id and to model then show to the form
        log.info("====> GET /rating/update/{} <====", id);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rating and return Rating list
        log.info("====> POST /rating/update/{} <====", id);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Rating by Id and delete the Rating, return to Rating list
        log.info("====> GET /rating/delete/{} <====", id);
        return "redirect:/rating/list";
    }
}
