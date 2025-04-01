package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.mapper.CurveMapper;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The Curve service
 */
@Slf4j
@Service
public class CurveService extends AbstractCrudService<CurvePoint, CurvePointDto> {

    /**
     * Constructor
     *
     * @param mapper     the mapper
     * @param repository the repository
     */
    public CurveService(@Qualifier("curveMapperImpl") CurveMapper mapper,
                        CurvePointRepository repository) {
        super(mapper, repository);
    }

}
