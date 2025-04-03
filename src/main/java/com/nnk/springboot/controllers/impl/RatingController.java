package com.nnk.springboot.controllers.impl;

import com.nnk.springboot.controllers.AbstractCrudController;
import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.service.impl.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The Rating controller
 */
@Controller
@Slf4j
@RequestMapping(path = "/rating")
public class RatingController extends AbstractCrudController<RatingDto, RatingService> {

    /**
     * Constructor
     *
     * @param ratingService the rating service
     */
    public RatingController(RatingService ratingService) {
        super(ratingService, "rating", RatingDto.class);
    }
}