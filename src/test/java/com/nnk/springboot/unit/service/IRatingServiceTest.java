package com.nnk.springboot.unit.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.exceptions.EntityMissingException;
import com.nnk.springboot.mapper.RatingMapper;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.IRatingService;
import com.nnk.springboot.service.impl.RatingService;
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
 * The IRatingServiceTest unit test
 */
@ExtendWith(MockitoExtension.class)
public class IRatingServiceTest {

    @Mock
    private RatingMapper ratingMapper;
    @Mock
    private RatingRepository ratingRepository;

    private IRatingService ratingService;

    /**
     * Setup before each test
     */
    @BeforeEach
    public void setUp() {
        ratingService = new RatingService(ratingMapper, ratingRepository);
    }


    /**
     * Test findAll
     * Given: A list of rating
     * When: findAll
     * Then: Return the rating list
     */
    @Test
    public void givenRatingList_whenFindAll_thenReturnRatingListDto() {
        // Given
        when(ratingRepository.findAll()).thenReturn(new ArrayList<>());

        // When
        List<RatingDto> ratingDtoList = ratingService.findAll();

        // Then
        assertEquals(new ArrayList<>(), ratingDtoList);
    }

    /**
     * Test findById
     * Given: A rating id
     * When: findById
     * Then: Return the rating
     */
    @Test
    public void givenExistingBidId_whenFindById_thenReturnBidDto() {
        // Given
        RatingDto ratingDtoExpected = new RatingDto();
        ratingDtoExpected.setId(1);
        ratingDtoExpected.setFitchRating("fitchRating");
        ratingDtoExpected.setMoodysRating("moodysRating");
        ratingDtoExpected.setSandPRating("sandPRRating");
        ratingDtoExpected.setOrderNumber(1);

        when(ratingRepository.findById(anyInt())).thenReturn(Optional.of(new Rating()));
        when(ratingMapper.toRatingDto(any(Rating.class))).thenReturn(ratingDtoExpected);

        // When
        RatingDto ratingDtoActual = ratingService.findById(anyInt());

        // Then
        assertEquals(ratingDtoExpected, ratingDtoActual);
    }

    /**
     * Test findById
     * Given: A non existing rating id
     * When: findById
     * Then: Throw EntityMissingException
     */
    @Test
    public void givenNonExistingRatingId_whenFindById_thenThrowEntityMissingException() {
        // Given
        when(ratingRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityMissingException.class,
                () -> ratingService.findById(anyInt())
        );
    }

    /**
     * Test update
     * Given: A ratingDto
     * When: update
     * Then: Rating is updated
     */
    @Test
    public void givenRatingDto_whenSave_thenRatingIsSaved() {
        // Given
        RatingDto ratingDto = new RatingDto();
        Rating rating = new Rating();

        when(ratingMapper.toRating(ratingDto)).thenReturn(rating);

        // When
        ratingService.create(ratingDto);

        // Then
        verify(ratingRepository, times(1)).save(rating);
    }

    /**
     * Test update
     * Given: A ratingDto with existing Id
     * When: update
     * Then: Rating is updated
     */
    @Test
    public void givenExistingRatingDto_whenUpdate_thenRatingIsUpdated() {
        // Given
        RatingDto ratingDto = new RatingDto();
        ratingDto.setId(1);

        Rating rating = new Rating();
        rating.setId(1);

        when(ratingRepository.findById(ratingDto.getId())).thenReturn(Optional.of(rating));

        // When
        ratingService.update(ratingDto);

        // Then
        verify(ratingRepository, times(1)).save(any(Rating.class));
    }

    /**
     * Test update
     * Given: A non existing ratingDto
     * When: update
     * Then: Throw EntityMissingException
     */
    @Test
    public void givenNonExistingRatingDto_whenUpdate_thenThrowEntityMissingException() {
        // Given
        RatingDto ratingDto = new RatingDto();
        ratingDto.setId(1);

        when(ratingRepository.findById(ratingDto.getId())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityMissingException.class,
                () -> ratingService.update(ratingDto)
        );
        verify(ratingRepository, times(0)).save(any(Rating.class));
    }

    /**
     * Test delete
     * Given: A rating Id
     * When: delete
     * Then: Rating Deleted
     */
    @Test
    public void givenExistingRatingId_whenDelete_thenRatingIsDeleted() {
        // Given
        when(ratingRepository.findById(anyInt())).thenReturn(Optional.of(new Rating()));

        // When
        ratingService.delete(anyInt());

        // Then
        verify(ratingRepository, times(1)).delete(any(Rating.class));
    }

    /**
     * Test delete
     * Given: A non-existing rating Id
     * When: delete
     * Then: Throw EntityMissingException
     */
    @Test
    public void givenNonExistingRatingId_whenDelete_thenThrowEntityMissingException() {
        // Given
        when(ratingRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityMissingException.class,
                () -> ratingService.delete(anyInt())
        );
    }


}
