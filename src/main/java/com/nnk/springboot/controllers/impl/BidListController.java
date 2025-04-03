package com.nnk.springboot.controllers.impl;


import com.nnk.springboot.controllers.AbstractCrudController;
import com.nnk.springboot.dto.BidDto;
import com.nnk.springboot.service.impl.BidListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The BidList controller
 */
@Controller
@Slf4j
@RequestMapping(path = "/bidList")
public class BidListController extends AbstractCrudController<BidDto, BidListService> {

    /**
     * Constructor
     *
     * @param bidListService the bidListService
     */
    public BidListController(BidListService bidListService) {
        super(bidListService, "bidList", BidDto.class);
    }
}