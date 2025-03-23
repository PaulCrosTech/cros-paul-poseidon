package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.exceptions.EntityMissingException;
import com.nnk.springboot.mapper.RatingMapper;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.IRatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * The Rating service
 */
@Service
@Slf4j
public class RatingService implements IRatingService {


    private final RatingMapper ratingMapper;
    private final RatingRepository ratingRepository;
    
    /**
     * Constructor
     *
     * @param ratingMapper     the ratingMapper
     * @param ratingRepository the ratingRepository
     */
    public RatingService(RatingMapper ratingMapper,
                         RatingRepository ratingRepository) {
        this.ratingMapper = ratingMapper;
        this.ratingRepository = ratingRepository;
    }

    /**
     * Find all ratings
     *
     * @return the list of ratings
     */
    @Override
    public List<RatingDto> findAll() {

        List<Rating> ratings = ratingRepository.findAll();
        List<RatingDto> ratingDtoList = new ArrayList<>();

        ratings.forEach(rating -> {
            ratingDtoList.add(ratingMapper.toRatingDto(rating));
        });

        return ratingDtoList;

    }

    /**
     * Find rating by id
     *
     * @param id the id of the rating to find
     * @return the rating
     * @throws EntityMissingException the rating not found exception
     */
    @Override
    public RatingDto findById(Integer id) throws EntityMissingException {
        log.debug("====> rating by id {} <====", id);
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new EntityMissingException("Rating not found with id : " + id));
        return ratingMapper.toRatingDto(rating);
    }

    /**
     * Create a rating in the database
     *
     * @param ratingDto the rating to add
     */
    @Transactional
    @Override
    public void create(RatingDto ratingDto) {
        log.debug("====> creating a new rating <====");

        ratingRepository.save(ratingMapper.toRating(ratingDto));

        log.debug("====> rating created <====");

    }

    /**
     * Update a rating in the database
     *
     * @param ratingDto the rating to update
     * @throws EntityMissingException the rating not found exception
     */
    @Transactional
    @Override
    public void update(RatingDto ratingDto) throws EntityMissingException {
        log.debug("====> update the rating with id {} <====", ratingDto.getId());

        Rating rating = ratingRepository.findById(ratingDto.getId())
                .orElseThrow(
                        () -> new EntityMissingException("Rating not found with id : " + ratingDto.getId())
                );

        rating.setMoodysRating(ratingDto.getMoodysRating());
        rating.setSandPRating(ratingDto.getSandPRating());
        rating.setFitchRating(ratingDto.getFitchRating());
        rating.setOrderNumber(ratingDto.getOrderNumber());
        ratingRepository.save(rating);

        log.debug("====> rating updated <====");
    }

    /**
     * Delete a rating in the database
     *
     * @param id the id of the rating to delete
     * @throws EntityMissingException the rating not found exception
     */
    @Transactional
    @Override
    public void delete(Integer id) throws EntityMissingException {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(
                        () -> new EntityMissingException("Rating not found with id : " + id)
                );
        ratingRepository.delete(rating);
    }
}
