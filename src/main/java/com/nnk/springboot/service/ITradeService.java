package com.nnk.springboot.service;


import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.exceptions.EntityMissingException;

import java.util.List;

/**
 * The ICurveService interface
 */
public interface ITradeService {


    /**
     * Find all trades
     *
     * @return the list of trades
     */
    List<TradeDto> findAll();


    /**
     * Find trade by id
     *
     * @param id the id of the trade to find
     * @return the trade
     * @throws EntityMissingException the trade not found exception
     */
    TradeDto findById(Integer id) throws EntityMissingException;


    /**
     * Create a trade in the database
     *
     * @param tradeDto the trade to add
     */
    void create(TradeDto tradeDto);


    /**
     * Update a trade in the database
     *
     * @param tradeDto the trade to update
     * @throws EntityMissingException the trade not found exception
     */
    void update(TradeDto tradeDto) throws EntityMissingException;


    /**
     * Delete a trade in the database
     *
     * @param id the id of the trade to delete
     * @throws EntityMissingException the trade not found exception
     */
    void delete(Integer id) throws EntityMissingException;
}
