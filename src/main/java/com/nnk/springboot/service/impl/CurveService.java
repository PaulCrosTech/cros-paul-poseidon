package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.exceptions.EntityMissingException;
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
     * Find CurvePoint by id
     *
     * @param id the id of the curve point to find
     * @return the curve point
     * @throws EntityMissingException the curve point not found exception
     */
    @Override
    public CurvePointDto findById(Integer id) throws EntityMissingException {
        log.debug("====> find curve point by id {} <====", id);
        CurvePoint curvePoint = curvePointRepository.findById(id)
                .orElseThrow(() -> new EntityMissingException("Curve point not found with id : " + id));
        return curveMapper.toCurvePointDto(curvePoint);
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

    /**
     * Update a curve point in the database
     *
     * @param curvePointDto the curve point to update
     * @throws EntityMissingException the curve point not found exception
     */
    @Transactional
    @Override
    public void update(CurvePointDto curvePointDto) throws EntityMissingException {
        log.debug("====> update the curve point with id {} <====", curvePointDto.getId());

        CurvePoint curvePoint = curvePointRepository.findById(curvePointDto.getId())
                .orElseThrow(
                        () -> new EntityMissingException("Curve point not found with id : " + curvePointDto.getId())
                );

        curvePoint.setTerm(curvePointDto.getTerm());
        curvePoint.setValue(curvePointDto.getValue());
        curvePointRepository.save(curvePoint);
        log.debug("====> curve point updated <====");
    }

    /**
     * Delete a curve point in the database
     *
     * @param id the id of the curve point to delete
     * @throws EntityMissingException the curve point not found exception
     */
    @Transactional
    @Override
    public void delete(Integer id) throws EntityMissingException {
        CurvePoint curvePoint = curvePointRepository.findById(id)
                .orElseThrow(
                        () -> new EntityMissingException("Curve point not found with id : " + id)
                );
        curvePointRepository.delete(curvePoint);
    }
}
