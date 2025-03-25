package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.AlertClass;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.dto.FlashMessage;
import com.nnk.springboot.exceptions.EntityMissingException;
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
@RequestMapping(path = "/curvePoint")
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
    @GetMapping("/list")
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
    @GetMapping("/add")
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
    @PostMapping("/validate")
    public String validate(@Valid CurvePointDto curvePointDto,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {

        log.info("====> POST /curvePoint/validate <====");

        if (result.hasErrors()) {
            log.debug("====> Form contains error <====");
            return "curvePoint/add";
        }

        curveService.create(curvePointDto);

        log.info("====> Curve Point created successfully <====");
        FlashMessage flashMessage = new FlashMessage(AlertClass.ALERT_SUCCESS, "Curve Point created successfully");
        redirectAttributes.addFlashAttribute("flashMessage", flashMessage);
        return "redirect:/curvePoint/list";
    }

    /**
     * Display the Curve update form
     *
     * @param id                 the id of the curve to update
     * @param model              the model
     * @param redirectAttributes the redirectAttributes
     * @return the Curve update page
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        log.info("====> GET /curvePoint/update/{} <====", id);

        try {
            CurvePointDto curvePointDto = curveService.findById(id);
            model.addAttribute("curvePointDto", curvePointDto);
        } catch (EntityMissingException e) {
            log.error("====> Error : {} <====", e.getMessage());
            redirectAttributes.addFlashAttribute("flashMessage", new FlashMessage());
            return "redirect:/curvePoint/list";
        }

        return "curvePoint/update";
    }

    /**
     * Validate the Curve update and save it to the database
     *
     * @param id                 the id of the curve to update
     * @param curvePointDto      the curvePointDto
     * @param result             the result
     * @param redirectAttributes the redirectAttributes
     * @return the Curve update page
     */
    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id,
                            @Valid CurvePointDto curvePointDto,
                            BindingResult result,
                            RedirectAttributes redirectAttributes) {

        log.info("====> POST /curvePoint/update/{} <====", id);

        if (result.hasErrors()) {
            return "curvePoint/update";
        }

        String logMessage = "Curve point updated successfully";
        FlashMessage flashMessage = new FlashMessage(AlertClass.ALERT_SUCCESS, "Curve point updated successfully");

        try {
            curveService.update(curvePointDto);
        } catch (EntityMissingException e) {
            logMessage = e.getMessage();
            flashMessage = new FlashMessage();
        }

        log.info("====> {} <====", logMessage);
        redirectAttributes.addFlashAttribute("flashMessage", flashMessage);

        return "redirect:/curvePoint/list";
    }


    /**
     * Delete a Curve
     *
     * @param id                 the id of the curve to delete
     * @param redirectAttributes the redirectAttributes
     * @return the Curve list page
     */
    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id,
                            RedirectAttributes redirectAttributes) {

        log.info("====> GET /curvePoint/delete/{} <====", id);

        String logMessage = "Curve point deleted successfully";
        FlashMessage flashMessage = new FlashMessage(AlertClass.ALERT_SUCCESS, "Curve point deleted successfully");

        try {
            curveService.delete(id);
        } catch (EntityMissingException e) {
            logMessage = e.getMessage();
            flashMessage = new FlashMessage();
        }

        log.info("====> {} <====", logMessage);
        redirectAttributes.addFlashAttribute("flashMessage", flashMessage);

        return "redirect:/curvePoint/list";
    }
}
