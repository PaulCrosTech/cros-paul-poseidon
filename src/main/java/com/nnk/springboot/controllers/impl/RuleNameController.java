package com.nnk.springboot.controllers.impl;


import com.nnk.springboot.controllers.AbstractCrudController;
import com.nnk.springboot.dto.RuleDto;
import com.nnk.springboot.service.impl.RuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for ruleName
 */
@Controller
@Slf4j
@RequestMapping(path = "/ruleName")
public class RuleNameController extends AbstractCrudController<RuleDto, RuleService> {

    /**
     * Constructor
     *
     * @param ruleService the service
     */
    public RuleNameController(RuleService ruleService) {
        super(ruleService, "ruleName", RuleDto.class);
    }
}