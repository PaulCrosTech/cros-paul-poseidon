package com.nnk.springboot.mapper;


import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.dto.BidDto;
import org.mapstruct.Mapper;


/**
 * The Bid mapper
 */
@Mapper(componentModel = "spring")
public interface BidMapper {

    /**
     * Convert a BidDto to a Bid
     *
     * @param bidDto the bidDto to convert
     * @return the Bid
     */
    Bid toBid(BidDto bidDto);


    /**
     * Convert a Bid to a BidDto
     *
     * @param bid the bid to convert
     * @return the BidDto
     */
    BidDto toBidDto(Bid bid);
}
