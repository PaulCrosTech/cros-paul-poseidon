package com.nnk.springboot.unit.mapper;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.dto.BidDto;
import com.nnk.springboot.mapper.BidMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * BidMapper unit tests.
 */
@ExtendWith(MockitoExtension.class)
public class BidMapperTest {

    private static BidMapper BidMapper;

    private BidDto bidDto;
    private Bid bid;

    /**
     * Sets up for all tests.
     */
    @BeforeAll
    public static void setUp() {
        BidMapper = Mappers.getMapper(BidMapper.class);
    }

    /**
     * Sets up before each test.
     */
    @BeforeEach
    public void setUpPerTest() {

        bidDto = new BidDto();
        bidDto.setId(1);
        bidDto.setBidQuantity(1d);
        bidDto.setAccount("account");
        bidDto.setType("type");

        bid = new Bid();
        bid.setId(1);
        bid.setBidQuantity(1d);
        bid.setAccount("account");
        bid.setType("type");

    }

    /**
     * Test toDto
     * Given: A Bid
     * When: toDto
     * Then: Return a BidDto
     */
    @Test
    public void givenBid_whentoDto_thenReturnBidDto() {

        // When
        BidDto bidDtoActual = BidMapper.toDto(bid);

        // Then
        assertEquals(bid.getId(), bidDtoActual.getId());
        assertEquals(bid.getAccount(), bidDtoActual.getAccount());
        assertEquals(bid.getType(), bidDtoActual.getType());
        assertEquals(bid.getBidQuantity(), bidDtoActual.getBidQuantity());
    }


    /**
     * Test toDomain
     * Given: A BidDto
     * When: toDomain
     * Then: Return a Bid
     */
    @Test
    public void givenBidDto_whentoDomain_thenReturnBid() {

        // When
        Bid bidActual = BidMapper.toDomain(bidDto);

        // Then
        assertEquals(bidDto.getId(), bidActual.getId());
        assertEquals(bidDto.getAccount(), bidActual.getAccount());
        assertEquals(bidDto.getType(), bidActual.getType());
        assertEquals(bidDto.getBidQuantity(), bidActual.getBidQuantity());


    }

}
