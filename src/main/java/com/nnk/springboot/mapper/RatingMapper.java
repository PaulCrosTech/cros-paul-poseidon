package com.nnk.springboot.mapper;


import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;
import org.mapstruct.Mapper;

/**
 * The RatingMapper interface
 */
@Mapper(componentModel = "spring")
public interface RatingMapper extends IMapper<Rating, RatingDto> {
}
