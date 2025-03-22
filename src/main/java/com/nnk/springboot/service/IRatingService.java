package com.nnk.springboot.service;

import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.exceptions.EntityMissingException;

import java.util.List;

/**
 * The IRatingService interface
 */
public interface IRatingService {


    /**
     * Find all ratings
     *
     * @return the list of ratings
     */
    List<RatingDto> findAll();

    /**
     * Find rating by id
     *
     * @param id the id of the rating to find
     * @return the rating
     * @throws EntityMissingException the rating not found exception
     */
    RatingDto findById(Integer id) throws EntityMissingException;

    /**
     * Create a rating in the database
     *
     * @param ratingDto the rating to add
     */
    void create(RatingDto ratingDto);

    /**
     * Update a rating in the database
     *
     * @param ratingDto the rating to update
     * @throws EntityMissingException the rating not found exception
     */
    void update(RatingDto ratingDto) throws EntityMissingException;

    /**
     * Delete a rating in the database
     *
     * @param id the id of the rating to delete
     * @throws EntityMissingException the rating not found exception
     */
    void delete(Integer id) throws EntityMissingException;
}
