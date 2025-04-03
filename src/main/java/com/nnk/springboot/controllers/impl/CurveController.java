package com.nnk.springboot.controllers.impl;


import com.nnk.springboot.controllers.AbstractCrudController;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.service.impl.CurveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The Curve controller
 */
@Controller
@Slf4j
@RequestMapping(path = "/curvePoint")
public class CurveController extends AbstractCrudController<CurvePointDto, CurveService> {

    /**
     * Constructor
     *
     * @param curveService the Curve service
     */
    public CurveController(CurveService curveService) {
        super(curveService, "curvePoint", CurvePointDto.class);
    }
}