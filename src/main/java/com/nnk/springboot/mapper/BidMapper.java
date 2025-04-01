package com.nnk.springboot.mapper;


import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.dto.BidDto;
import org.mapstruct.Mapper;

/**
 * The Bid mapper
 */
@Mapper(componentModel = "spring")
public interface BidMapper extends IMapper<Bid, BidDto> {
}
