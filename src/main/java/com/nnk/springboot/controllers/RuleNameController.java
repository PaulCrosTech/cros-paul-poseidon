package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.dto.RuleDto;
import com.nnk.springboot.service.IRuleService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * The RuleName controller
 */
@Controller
@Slf4j
@RequestMapping(path = "/ruleName")
public class RuleNameController {


    private final IRuleService ruleService;

    /**
     * Constructor
     *
     * @param ruleService the ruleService
     */
    public RuleNameController(IRuleService ruleService) {
        this.ruleService = ruleService;
    }

    /**
     * Add generic attributes to the model
     *
     * @param model the model
     */
    @ModelAttribute
    private void addAttributes(Model model) {
        model.addAttribute("menuActivated", "ruleName");
    }

    /**
     * Display the RuleName list page
     *
     * @param model the model
     * @return the RuleName list page
     */
    @RequestMapping("/list")
    public String home(Model model) {
        log.info("====> GET /ruleName/list <====");

        List<RuleDto> ruleDtoList = ruleService.findAll();
        model.addAttribute("ruleDtoList", ruleDtoList);

        return "ruleName/list";
    }

    @GetMapping("/add")
    public String addRuleForm(Rule bid) {
        log.info("====> GET /ruleName/add <====");
        return "ruleName/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid Rule rule, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return RuleName list
        log.info("====> POST /ruleName/validate <====");
        return "ruleName/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get RuleName by Id and to model then show to the form
        log.info("====> GET /ruleName/update/{} <====", id);
        return "ruleName/update";
    }

    @PostMapping("/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid Rule rule,
                                 BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update RuleName and return RuleName list
        log.info("====> POST /ruleName/update/{} <====", id);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // TODO: Find RuleName by Id and delete the RuleName, return to RuleName list
        log.info("====> GET /ruleName/delete/{} <====", id);
        return "redirect:/ruleName/list";
    }
}
