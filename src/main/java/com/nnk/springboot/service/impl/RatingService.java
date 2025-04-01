package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.mapper.RatingMapper;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


/**
 * The Rating service
 */
@Service
@Slf4j
public class RatingService extends AbstractCrudService<Rating, RatingDto> {
    /**
     * Constructor
     *
     * @param mapper     the mapper
     * @param repository the repository
     */
    public RatingService(@Qualifier("ratingMapperImpl") RatingMapper mapper,
                         RatingRepository repository) {
        super(mapper, repository);
    }
}
