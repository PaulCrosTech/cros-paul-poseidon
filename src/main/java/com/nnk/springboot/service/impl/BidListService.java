package com.nnk.springboot.service.impl;


import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.dto.BidDto;
import com.nnk.springboot.mapper.BidMapper;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.IBidListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * The BidList service
 */
@Service
@Slf4j
public class BidListService implements IBidListService {


    private final BidMapper bidMapper;
    private final BidListRepository bidListRepository;

    /**
     * Constructor
     *
     * @param bidListRepository the bidListRepository
     */
    public BidListService(BidListRepository bidListRepository,
                          BidMapper bidMapper) {
        this.bidListRepository = bidListRepository;
        this.bidMapper = bidMapper;
    }


    /**
     * Find all bid
     *
     * @return the list of bid
     */
    @Override
    public List<BidDto> findAll() {
        List<Bid> bids = bidListRepository.findAll();
        
        List<BidDto> bidDtoList = new ArrayList<>();

        bids.forEach(bid -> {
            bidDtoList.add(bidMapper.toBidDtoList(bid));
        });

        return bidDtoList;

    }

    /**
     * Add a bid in the database
     *
     * @param bidDto the bid to add
     */
    @Transactional
    @Override
    public void add(BidDto bidDto) {
        log.debug("====> creating a new bid <====");

        bidListRepository.save(bidMapper.toBid(bidDto));

        log.debug("====> bid created <====");

    }
}
