package com.nnk.springboot.unit.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.exceptions.EntityMissingException;
import com.nnk.springboot.mapper.TradeMapper;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.ICrudService;
import com.nnk.springboot.service.impl.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * The ITradeServiceTest unit test
 */
@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {


    @Mock
    private TradeRepository tradeRepository;
    @Mock
    private TradeMapper tradeMapper;

    private ICrudService<TradeDto> tradeService;

    /**
     * Setup before each test
     */
    @BeforeEach
    public void setUp() {
        tradeService = new TradeService(tradeMapper, tradeRepository);
    }

    /**
     * Test findAll
     * Given: A list of trade
     * When: findAll
     * Then: Return the TradeDto list
     */
    @Test
    public void givenTradeList_whenFindAll_thenReturnTradeListDto() {
        // Given
        when(tradeRepository.findAll()).thenReturn(new ArrayList<>());

        // When
        List<TradeDto> tradeDtoList = tradeService.findAll();

        // Then
        assertEquals(new ArrayList<>(), tradeDtoList);
    }

    /**
     * Test findById
     * Given: A trade id
     * When: findById
     * Then: Return the TradeDto
     */
    @Test
    public void givenExistingTradeId_whenFindById_thenReturnTradeDto() {
        // Given
        TradeDto tradeDtoExpected = new TradeDto();
        tradeDtoExpected.setId(1);
        tradeDtoExpected.setAccount("account");
        tradeDtoExpected.setType("type");
        tradeDtoExpected.setBuyQuantity(1d);

        when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(new Trade()));
        when(tradeMapper.toDto(any(Trade.class))).thenReturn(tradeDtoExpected);

        // When
        TradeDto tradeDtoActual = tradeService.findById(anyInt());

        // Then
        assertEquals(tradeDtoExpected, tradeDtoActual);
    }

    /**
     * Test findById
     * Given: A non existing trade id
     * When: findById
     * Then: Throw EntityMissingException
     */
    @Test
    public void givenNonExistingTradeId_whenFindById_thenThrowEntityMissingException() {
        // Given
        when(tradeRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityMissingException.class,
                () -> tradeService.findById(anyInt())
        );
    }

    /**
     * Test save
     * Given: A tradeDto
     * When: save
     * Then: Trade is saved
     */
    @Test
    public void givenTradeDto_whenSave_thenTradeIsSaved() {
        // Given
        TradeDto tradeDto = new TradeDto();
        Trade trade = new Trade();

        when(tradeMapper.toDomain(tradeDto)).thenReturn(trade);

        // When
        tradeService.create(tradeDto);

        // Then
        verify(tradeRepository, times(1)).save(trade);
    }

    /**
     * Test update
     * Given: A tradeDto
     * When: update
     * Then: Trade is updated
     */
    @Test
    public void givenExistingTradeDto_whenUpdate_thenTradeIsUpdated() {
        // Given
        TradeDto tradeDto = new TradeDto();
        tradeDto.setId(1);

        Trade trade = new Trade();
        trade.setId(1);

        when(tradeRepository.findById(trade.getId())).thenReturn(Optional.of(trade));

        // When
        tradeService.update(tradeDto);

        // Then
        verify(tradeRepository, times(1)).save(any(Trade.class));
    }

    /**
     * Test update
     * Given: A non existing tradeDto
     * When: update
     * Then: Throw EntityMissingException
     */
    @Test
    public void givenNonExistingTradeDto_whenUpdate_thenThrowEntityMissingException() {
        // Given
        TradeDto tradeDto = new TradeDto();
        tradeDto.setId(1);

        when(tradeRepository.findById(tradeDto.getId())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityMissingException.class,
                () -> tradeService.update(tradeDto)
        );
        verify(tradeRepository, times(0)).save(any(Trade.class));
    }

    /**
     * Test delete
     * Given: A trade id
     * When: delete
     * Then: Trade is deleted
     */
    @Test
    public void givenExistingTradeId_whenDelete_thenTradeIsDeleted() {
        // Given
        when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(new Trade()));

        // When
        tradeService.delete(anyInt());

        // Then
        verify(tradeRepository, times(1)).delete(any(Trade.class));
    }

    /**
     * Test delete
     * Given: A non existing trade id
     * When: delete
     * Then: Throw EntityMissingException
     */
    @Test
    public void givenNonExistingTradeId_whenDelete_thenThrowEntityMissingException() {
        // Given
        when(tradeRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityMissingException.class,
                () -> tradeService.delete(anyInt())
        );
    }

}
