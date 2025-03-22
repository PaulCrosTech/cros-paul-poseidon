package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.AlertClass;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.dto.FlashMessage;
import com.nnk.springboot.service.ICurveService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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


    /**
     * Display the Curve add form
     *
     * @param model the model
     * @return the Curve add page
     */
    @GetMapping("/curvePoint/add")
    public String addBidForm(Model model) {
        log.info("====> GET /curvePoint/add <====");
        model.addAttribute("curvePointDto", new CurvePointDto());
        return "curvePoint/add";
    }


    /**
     * Validate the Curve and save it to the database
     *
     * @param curvePointDto      the curvePointDto
     * @param result             the result
     * @param redirectAttributes the redirectAttributes
     * @return the Curve add page
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePointDto curvePointDto,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {

        log.info("====> POST /curvePoint/validate <====");

        if (result.hasErrors()) {
            log.debug("====> POST /curvePoint/validate : form contains error <====");
            return "curvePoint/add";
        }

        curveService.create(curvePointDto);

        log.info("====> POST /curvePoint/validate : Curve Point created successfully <====");
        FlashMessage flashMessage = new FlashMessage(AlertClass.ALERT_SUCCESS, "Curve Point created successfully");
        redirectAttributes.addFlashAttribute("flashMessage", flashMessage);
        return "redirect:/curvePoint/list";
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
