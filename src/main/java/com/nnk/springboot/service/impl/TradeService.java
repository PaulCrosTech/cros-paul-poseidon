package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.exceptions.EntityMissingException;
import com.nnk.springboot.mapper.TradeMapper;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.ITradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * The Trade service
 */
@Service
@Slf4j
public class TradeService implements ITradeService {

    private final TradeRepository tradeRepository;
    private final TradeMapper tradeMapper;

    /**
     * Constructor
     *
     * @param tradeRepository the tradeRepository
     * @param tradeMapper     the tradeMapper
     */
    public TradeService(TradeRepository tradeRepository, TradeMapper tradeMapper) {
        this.tradeRepository = tradeRepository;
        this.tradeMapper = tradeMapper;
    }

    /**
     * Find all trades
     *
     * @return the list of trades
     */
    @Override
    public List<TradeDto> findAll() {
        List<Trade> trades = tradeRepository.findAll();
        List<TradeDto> tradeDtoList = new ArrayList<>();

        trades.forEach(trade -> {
            tradeDtoList.add(tradeMapper.toTradeDto(trade));
        });

        return tradeDtoList;
    }

    /**
     * Find trade by id
     *
     * @param id the id of the trade to find
     * @return the trade
     * @throws EntityMissingException the trade not found exception
     */
    @Override
    public TradeDto findById(Integer id) throws EntityMissingException {
        log.debug("====> find trade point by id {} <====", id);
        Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> new EntityMissingException("Trade not found with id : " + id));
        return tradeMapper.toTradeDto(trade);
    }


    /**
     * Create a trade in the database
     *
     * @param tradeDto the trade to add
     */
    @Override
    @Transactional
    public void create(TradeDto tradeDto) {
        log.debug("====> creating a new trade <====");

        tradeRepository.save(tradeMapper.toTrade(tradeDto));

        log.debug("====> trade created <====");
    }

    /**
     * Update a trade in the database
     *
     * @param tradeDto the trade to update
     * @throws EntityMissingException the trade not found exception
     */
    @Override
    @Transactional
    public void update(TradeDto tradeDto) throws EntityMissingException {
        log.debug("====> update the trade with id {} <====", tradeDto.getId());

        Trade trade = tradeRepository.findById(tradeDto.getId())
                .orElseThrow(
                        () -> new EntityMissingException("Trade not found with id : " + tradeDto.getId())
                );

        trade.setAccount(tradeDto.getAccount());
        trade.setType(tradeDto.getType());
        trade.setBuyQuantity(tradeDto.getBuyQuantity());
        tradeRepository.save(trade);
        log.debug("====> trade updated <====");
    }

    /**
     * Delete a trade in the database
     *
     * @param id the id of the trade to delete
     * @throws EntityMissingException the trade not found exception
     */
    @Override
    @Transactional
    public void delete(Integer id) throws EntityMissingException {
        Trade trade = tradeRepository.findById(id)
                .orElseThrow(
                        () -> new EntityMissingException("Trade not found with id : " + id)
                );
        tradeRepository.delete(trade);
    }
}
