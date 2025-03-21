package com.nnk.springboot.service;


import com.nnk.springboot.dto.BidDto;

import java.util.List;

/**
 * The Interface BidList service
 */
public interface IBidListService {

    /**
     * Find all bid
     *
     * @return the list of bid
     */
    List<BidDto> findAll();


    /**
     * Add a bid in the database
     *
     * @param bidDto the bid to add
     */
    void add(BidDto bidDto);

}
