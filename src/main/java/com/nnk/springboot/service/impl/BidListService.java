package com.nnk.springboot.service.impl;


import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.dto.BidDto;
import com.nnk.springboot.exceptions.EntityMissingException;
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
     * @param bidListRepository the bidList repository
     * @param bidMapper         the bid mapper
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
            bidDtoList.add(bidMapper.toBidDto(bid));
        });

        return bidDtoList;

    }

    /**
     * Find bid by id
     *
     * @param id the id of the bid to find
     * @return the bidDto
     * @throws EntityMissingException the bid not found exception
     */
    @Override
    public BidDto findById(Integer id) throws EntityMissingException {
        log.debug("====> find bid by id {} <====", id);
        Bid bid = bidListRepository.findById(id)
                .orElseThrow(() -> new EntityMissingException("Bid not found with id : " + id));
        return bidMapper.toBidDto(bid);
    }

    /**
     * Add a bid in the database
     *
     * @param bidDto the bid to add
     */
    @Transactional
    @Override
    public void create(BidDto bidDto) {
        log.debug("====> creating a new bid <====");

        bidListRepository.save(bidMapper.toBid(bidDto));

        log.debug("====> bid created <====");

    }

    /**
     * Update a bid in the database
     *
     * @param bidDto the bid to update
     * @throws EntityMissingException the bid not found exception
     */
    @Transactional
    @Override
    public void update(BidDto bidDto) throws EntityMissingException {
        log.debug("====> update the bid with id {} <====", bidDto.getId());

        Bid bid = bidListRepository.findById(bidDto.getId())
                .orElseThrow(
                        () -> new EntityMissingException("Bid not found with id : " + bidDto.getId())
                );

        bid.setAccount(bidDto.getAccount());
        bid.setType(bidDto.getType());
        bid.setBidQuantity(bidDto.getBidQuantity());
        bidListRepository.save(bid);
        log.debug("====> bid updated <====");
    }


    /**
     * Delete a bid from the database
     *
     * @param id the id of the bid to delete
     * @throws EntityMissingException the bid not found exception
     */
    @Transactional
    @Override
    public void delete(Integer id) throws EntityMissingException {
        Bid bid = bidListRepository.findById(id)
                .orElseThrow(
                        () -> new EntityMissingException("Bid not found with id : " + id)
                );
        bidListRepository.delete(bid);
    }
}
