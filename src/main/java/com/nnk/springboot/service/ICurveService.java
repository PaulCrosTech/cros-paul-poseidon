package com.nnk.springboot.service;

import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.exceptions.EntityMissingException;

import java.util.List;

/**
 * The ICurveService interface
 */
public interface ICurveService {


    /**
     * Find all curve points
     *
     * @return the list of curve points
     */
    List<CurvePointDto> findAll();


    /**
     * Find curve point by id
     *
     * @param id the id of the curve point to find
     * @return the curve point
     * @throws EntityMissingException the curve point not found exception
     */
    CurvePointDto findById(Integer id) throws EntityMissingException;


    /**
     * Create a curve point in the database
     *
     * @param curvePointDto the curve point to add
     */
    void create(CurvePointDto curvePointDto);

    /**
     * Update a curve point in the database
     *
     * @param curvePointDto the curve point to update
     * @throws EntityMissingException the curve point not found exception
     */
    void update(CurvePointDto curvePointDto) throws EntityMissingException;

    /**
     * Delete a curve point in the database
     *
     * @param id the id of the curve point to delete
     * @throws EntityMissingException the curve point not found exception
     */
    void delete(Integer id) throws EntityMissingException;
}
