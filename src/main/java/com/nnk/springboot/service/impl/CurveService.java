package com.nnk.springboot.service.impl;


import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.mapper.CurveMapper;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.ICurveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * The Curve service
 */
@Service
@Slf4j
public class CurveService implements ICurveService {


    private final CurveMapper curveMapper;
    private final CurvePointRepository curvePointRepository;

    /**
     * Constructor
     *
     * @param curveMapper          the curveMapper
     * @param curvePointRepository the curvePointRepository
     */
    public CurveService(CurveMapper curveMapper,
                        CurvePointRepository curvePointRepository) {
        this.curveMapper = curveMapper;
        this.curvePointRepository = curvePointRepository;
    }

    /**
     * Find all CurvePoint
     *
     * @return the list of CurvePointDto
     */
    @Override
    public List<CurvePointDto> findAll() {

        List<CurvePoint> curvePoints = curvePointRepository.findAll();
        List<CurvePointDto> curvePointDtoList = new ArrayList<>();

        curvePoints.forEach(curvePoint -> {
            curvePointDtoList.add(curveMapper.toCurvePointDto(curvePoint));
        });

        return curvePointDtoList;

    }

    /**
     * Create a curve point in the database
     *
     * @param curvePointDto the curve point to add
     */
    @Transactional
    @Override
    public void create(CurvePointDto curvePointDto) {
        log.debug("====> creating a new curve point <====");

        curvePointRepository.save(curveMapper.toCurvePoint(curvePointDto));

        log.debug("====> curve point created <====");

    }
}
