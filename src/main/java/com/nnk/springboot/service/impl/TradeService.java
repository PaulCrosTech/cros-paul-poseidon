package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.mapper.TradeMapper;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The Trade service
 */
@Service
@Slf4j
public class TradeService extends AbstractCrudService<Trade, TradeDto> {


    /**
     * Constructor
     *
     * @param mapper     the mapper
     * @param repository the repository
     */
    public TradeService(@Qualifier("tradeMapperImpl") TradeMapper mapper,
                        TradeRepository repository) {
        super(mapper, repository);
    }
}
