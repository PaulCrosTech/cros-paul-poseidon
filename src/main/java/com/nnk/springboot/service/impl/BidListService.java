package com.nnk.springboot.service.impl;


import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.dto.BidDto;
import com.nnk.springboot.mapper.BidMapper;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.AbstractCrudService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The BidList service
 */
@Service
public class BidListService extends AbstractCrudService<Bid, BidDto> {

    /**
     * Constructor
     *
     * @param mapper     the mapper
     * @param repository the repository
     */
    public BidListService(@Qualifier("bidMapperImpl") BidMapper mapper,
                          BidListRepository repository) {
        super(mapper, repository);
    }
}