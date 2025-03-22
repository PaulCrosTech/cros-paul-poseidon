package com.nnk.springboot.service;

import com.nnk.springboot.dto.CurvePointDto;

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

}
