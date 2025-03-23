package com.nnk.springboot.mapper;


import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;
import org.mapstruct.Mapper;

/**
 * The RatingMapper interface
 */
@Mapper(componentModel = "spring")
public interface RatingMapper {


    /**
     * Convert a RatingDto to a Rating
     *
     * @param ratingDto the ratingDto to convert
     * @return the Rating
     */
    Rating toRating(RatingDto ratingDto);


    /**
     * Convert a Rating to a RatingDto
     *
     * @param rating the rating to convert
     * @return the RatingDto
     */
    RatingDto toRatingDto(Rating rating);

}
