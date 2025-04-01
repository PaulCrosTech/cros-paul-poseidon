package com.nnk.springboot.mapper;


import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import org.mapstruct.Mapper;

/**
 * The Trade mapper
 */
@Mapper(componentModel = "spring")
public interface TradeMapper extends IMapper<Trade, TradeDto> {
}
