package com.nnk.springboot.mapper;


import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import org.mapstruct.Mapper;

/**
 * The Trade mapper
 */
@Mapper(componentModel = "spring")
public interface TradeMapper {

    /**
     * Convert a TradeDto to a Trade
     *
     * @param tradeDto the TradeDto to convert
     * @return the Trade
     */
    Trade toTrade(TradeDto tradeDto);

    /**
     * Convert a Trade to a TradeDto
     *
     * @param trade the Trade to convert
     * @return the TradeDto
     */
    TradeDto toTradeDto(Trade trade);

}
