package com.nnk.springboot.service;


import com.nnk.springboot.dto.BidDto;
import com.nnk.springboot.exceptions.EntityNotFoundException;

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
     * Find bid by id
     *
     * @param id the id of the bid to find
     * @return the bidDto
     * @throws EntityNotFoundException the bid not found exception
     */
    BidDto findById(Integer id) throws EntityNotFoundException;

    /**
     * Create a bid in the database
     *
     * @param bidDto the bid to add
     */
    void create(BidDto bidDto);

    /**
     * Update a bid in the database
     *
     * @param bidDto the bid to update
     * @throws EntityNotFoundException the bid not found exception
     */
    void update(BidDto bidDto) throws EntityNotFoundException;

    /**
     * Delete a bid in the database
     *
     * @param id the id of the bid to delete
     */
    void delete(Integer id) throws EntityNotFoundException;

}
