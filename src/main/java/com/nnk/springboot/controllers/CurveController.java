package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.service.ICurveService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The Curve controller
 */
@Controller
@Slf4j
public class CurveController {


    private final ICurveService curveService;

    /**
     * Constructor
     *
     * @param curveService the curveService
     */
    public CurveController(ICurveService curveService) {
        this.curveService = curveService;

    }

    /**
     * Add generic attributes to the model
     *
     * @param model the model
     */
    @ModelAttribute
    private void addAttributes(Model model) {
        model.addAttribute("menuActivated", "curve");
    }


    /**
     * Display the Curve list page
     *
     * @param model the model
     * @return the Curve list page
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        log.info("====> GET /curvePoint/list <====");

        List<CurvePointDto> curvePointList = curveService.findAll();
        model.addAttribute("curvePointList", curvePointList);

        // TODO : dans le template ci après, ce ne serait pas plutôt : crudPoint.curveId ?
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        log.info("====> GET /curvePoint/add <====");
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Curve list
        log.info("====> POST /curvePoint/validate <====");
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get CurvePoint by Id and to model then show to the form
        log.info("====> GET /curvePoint/update <====");
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                            BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Curve and return Curve list
        log.info("====> POST /curvePoint/update/{} <====", id);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
        log.info("====> GET /curvePoint/delete{} <====", id);
        return "redirect:/curvePoint/list";
    }
}
