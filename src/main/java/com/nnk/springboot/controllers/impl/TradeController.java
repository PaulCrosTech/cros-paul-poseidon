package com.nnk.springboot.controllers.impl;

import com.nnk.springboot.controllers.AbstractCrudController;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.service.impl.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The Trade controller
 */
@Controller
@Slf4j
@RequestMapping(path = "/trade")
public class TradeController extends AbstractCrudController<TradeDto, TradeService> {

    /**
     * Constructor
     *
     * @param tradeService the tradeService
     */
    public TradeController(TradeService tradeService) {
        super(tradeService, "trade", TradeDto.class);
    }
}