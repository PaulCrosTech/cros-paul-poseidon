package com.nnk.springboot.mapper;


import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import org.mapstruct.Mapper;

/**
 * The CurveMapper interface
 */
@Mapper(componentModel = "spring")
public interface CurveMapper {


    /**
     * Convert a CurvePointDto to a CurvePoint
     *
     * @param curvePointDto the curvePointDto to convert
     * @return the CurvePoint
     */
    CurvePoint toCurvePoint(CurvePointDto curvePointDto);

    /**
     * Convert a CurvePoint to a CurvePointDto
     *
     * @param curvePoint the curvePoint to convert
     * @return the CurvePointDto
     */
    CurvePointDto toCurvePointDto(CurvePoint curvePoint);
}
