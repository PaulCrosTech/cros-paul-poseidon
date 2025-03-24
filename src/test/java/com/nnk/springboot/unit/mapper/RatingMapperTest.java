package com.nnk.springboot.unit.mapper;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.mapper.RatingMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * RatingMapper unit tests.
 */
@ExtendWith(MockitoExtension.class)
public class RatingMapperTest {

    private static RatingMapper ratingMapper;

    private RatingDto ratingDto;
    private Rating rating;

    /**
     * Sets up for all tests.
     */
    @BeforeAll
    public static void setUp() {
        ratingMapper = Mappers.getMapper(RatingMapper.class);
    }


    /**
     * Sets up before each test.
     */
    @BeforeEach
    public void setUpPerTest() {

        ratingDto = new RatingDto();
        ratingDto.setId(1);
        ratingDto.setMoodysRating("moodysRating");
        ratingDto.setSandPRating("sandPRating");
        ratingDto.setFitchRating("fitchRating");
        ratingDto.setOrderNumber(1);

        rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("moodysRating");
        rating.setSandPRating("sandPRating");
        rating.setFitchRating("fitchRating");
        rating.setOrderNumber(1);
    }

    /**
     * Test toRatingDto
     * Given: A Rating
     * When: toRatingDto
     * Then: Return a RatingDto
     */
    @Test
    public void givenRating_whenToRatingDto_thenReturnRatingDto() {
        // When
        RatingDto ratingDtoActual = ratingMapper.toRatingDto(rating);

        // Then
        assertEquals(rating.getId(), ratingDtoActual.getId());
        assertEquals(rating.getMoodysRating(), ratingDtoActual.getMoodysRating());
        assertEquals(rating.getSandPRating(), ratingDtoActual.getSandPRating());
        assertEquals(rating.getFitchRating(), ratingDtoActual.getFitchRating());
        assertEquals(rating.getOrderNumber(), ratingDtoActual.getOrderNumber());
    }


    /**
     * Test toRating
     * Given: A RatingDto
     * When: toRating
     * Then: Return a Rating
     */
    @Test
    public void givenRatingDto_whenToRating_thenReturnRating() {

        // When
        Rating ratingActual = ratingMapper.toRating(ratingDto);

        // Then
        assertEquals(ratingDto.getId(), ratingActual.getId());
        assertEquals(ratingDto.getMoodysRating(), ratingActual.getMoodysRating());
        assertEquals(ratingDto.getSandPRating(), ratingActual.getSandPRating());
        assertEquals(ratingDto.getFitchRating(), ratingActual.getFitchRating());
        assertEquals(ratingDto.getOrderNumber(), ratingActual.getOrderNumber());

    }

}
