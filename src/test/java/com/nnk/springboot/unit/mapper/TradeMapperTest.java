package com.nnk.springboot.unit.mapper;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.mapper.TradeMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * TradeMapper unit tests.
 */
@ExtendWith(MockitoExtension.class)
public class TradeMapperTest {

    private Trade trade;
    private TradeDto tradeDto;

    private static TradeMapper tradeMapper;

    /**
     * Sets up for all tests.
     */
    @BeforeAll
    public static void setUp() {
        tradeMapper = Mappers.getMapper(TradeMapper.class);
    }


    /**
     * Sets up before each test.
     */
    @BeforeEach
    public void setUpPerTest() {

        trade = new Trade();
        trade.setId(1);
        trade.setAccount("account");
        trade.setType("type");
        trade.setBuyQuantity(1d);

        tradeDto = new TradeDto();
        tradeDto.setId(1);
        tradeDto.setAccount("account");
        tradeDto.setType("type");
        tradeDto.setBuyQuantity(1d);
    }

    /**
     * Test toDto
     * Given: A Trade
     * When: toDto
     * Then: Return a TradeDto
     */
    @Test
    public void givenTrade_whenToDto_thenReturnTradeDto() {

        // When
        TradeDto tradeDtoActual = tradeMapper.toDto(trade);

        // Then
        assertEquals(tradeDto.getId(), tradeDtoActual.getId());
        assertEquals(tradeDto.getAccount(), tradeDtoActual.getAccount());
        assertEquals(tradeDto.getType(), tradeDtoActual.getType());
        assertEquals(tradeDto.getBuyQuantity(), tradeDtoActual.getBuyQuantity());
    }

    /**
     * Test toDto
     * * Given: A null Trade
     * When: toDto
     * Then: Return null
     */
    @Test
    public void givenNull_whenToDto_thenReturnNull() {

        // When
        TradeDto tradeDtoActual = tradeMapper.toDto(null);

        // Then
        assertNull(tradeDtoActual);
    }

    /**
     * Test toDomain
     * Given: A TradeDto
     * When: toDomain
     * Then: Return a Trade
     */
    @Test
    public void givenTradeDto_whentoDomain_thenReturnTrade() {

        // When
        Trade tradeActual = tradeMapper.toDomain(tradeDto);

        // Then
        assertEquals(trade.getId(), tradeActual.getId());
        assertEquals(trade.getAccount(), tradeActual.getAccount());
        assertEquals(trade.getType(), tradeActual.getType());
        assertEquals(trade.getBuyQuantity(), tradeActual.getBuyQuantity());
    }

    /**
     * Test toDomain
     * Given: A null TradeDto
     * When: toDomain
     * Then: Return null
     */
    @Test
    public void givenNull_whenToDomain_thenReturnNull() {

        // When
        Trade tradeActual = tradeMapper.toDomain(null);

        // Then
        assertNull(tradeActual);
    }
}
