package com.nnk.springboot.unit.service;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.dto.BidDto;
import com.nnk.springboot.exceptions.EntityMissingException;
import com.nnk.springboot.mapper.BidMapper;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.IBidListService;
import com.nnk.springboot.service.impl.BidListService;
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
 * The IBidListServiceTest unit test
 */
@ExtendWith(MockitoExtension.class)
public class IBidListServiceTest {

    @Mock
    private BidMapper bidMapper;
    @Mock
    private BidListRepository bidListRepository;

    private IBidListService bidListService;

    /**
     * Setup before each test
     */
    @BeforeEach
    public void setUp() {
        bidListService = new BidListService(bidListRepository, bidMapper);
    }


    /**
     * Test findAll
     * Given: A list of bidList
     * When: findAll
     * Then: Return the bidList list
     */
    @Test
    public void givenBidList_whenFindAll_thenReturnBidListDto() {
        // Given
        when(bidListRepository.findAll()).thenReturn(new ArrayList<>());

        // When
        List<BidDto> bidDtoList = bidListService.findAll();

        // Then
        assertEquals(new ArrayList<>(), bidDtoList);
    }


    /**
     * Test findById
     * Given: A Bid Id
     * When: findById
     * Then: Return the BidDto
     */
    @Test
    public void givenExistingBidId_whenFindById_thenReturnBidDto() {
        // Given
        BidDto bidDtoExpected = new BidDto();
        bidDtoExpected.setId(1);
        bidDtoExpected.setAccount("account");
        bidDtoExpected.setType("type");
        bidDtoExpected.setBidQuantity(1d);

        when(bidListRepository.findById(anyInt())).thenReturn(Optional.of(new Bid()));
        when(bidMapper.toBidDto(any(Bid.class))).thenReturn(bidDtoExpected);

        // When
        BidDto bidDtoActual = bidListService.findById(anyInt());

        // Then
        assertEquals(bidDtoExpected, bidDtoActual);
    }


    /**
     * Test findById
     * Given: A non-existing Bid Id
     * When: findById
     * Then: Throw EntityMissingException
     */
    @Test
    public void givenNonExistingBidId_whenFindById_thenThrowEntityMissingException() {
        // Given
        when(bidListRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityMissingException.class,
                () -> bidListService.findById(anyInt())
        );
    }


    /**
     * Test create
     * Given: A BidDto
     * When: create
     * Then: Return void
     */
    @Test
    public void givenBidDto_whenSave_thenBidIsSaved() {
        // Given
        BidDto bidDto = new BidDto();
        Bid bid = new Bid();

        when(bidMapper.toBid(bidDto)).thenReturn(bid);

        // When
        bidListService.create(bidDto);

        // Then
        verify(bidListRepository, times(1)).save(bid);
    }


    /**
     * Test update
     * Given: A BidDto with existing Id
     * When: update
     * Then: Bid is updated
     */
    @Test
    public void givenExistingBidDto_whenUpdate_thenBidIsUpdated() {
        // Given
        BidDto bidDto = new BidDto();
        bidDto.setId(1);

        Bid bid = new Bid();
        bid.setId(1);

        when(bidListRepository.findById(bidDto.getId())).thenReturn(Optional.of(bid));

        // When
        bidListService.update(bidDto);

        // Then
        verify(bidListRepository, times(1)).save(any(Bid.class));
    }

    /**
     * Test update
     * Given: A BidDto with non-existing Id
     * When: update
     * Then: Throw EntityMissingException
     */
    @Test
    public void givenNonExistingBidDto_whenUpdate_thenThrowEntityMissingException() {
        // Given
        BidDto bidDto = new BidDto();
        bidDto.setId(1);

        when(bidListRepository.findById(bidDto.getId())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityMissingException.class,
                () -> bidListService.update(bidDto)
        );
        verify(bidListRepository, times(0)).save(any(Bid.class));
    }


    /**
     * Test delete
     * Given: A Bid Id
     * When: delete
     * Then: Bid Deleted
     */
    @Test
    public void givenExistingBidId_whenDelete_thenBidIsDeleted() {
        // Given
        when(bidListRepository.findById(anyInt())).thenReturn(Optional.of(new Bid()));

        // When
        bidListService.delete(anyInt());

        // Then
        verify(bidListRepository, times(1)).delete(any(Bid.class));
    }


    /**
     * Test delete
     * Given: A non-existing Bid Id
     * When: delete
     * Then: Throw EntityMissingException
     */
    @Test
    public void givenNonExistingBidId_whenDelete_thenThrowEntityMissingException() {
        // Given
        when(bidListRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityMissingException.class,
                () -> bidListService.delete(anyInt())
        );
    }

}
