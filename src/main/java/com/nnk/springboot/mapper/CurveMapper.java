package com.nnk.springboot.mapper;


import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import org.mapstruct.Mapper;

/**
 * The CurveMapper interface
 */
@Mapper(componentModel = "spring")
public interface CurveMapper extends IMapper<CurvePoint, CurvePointDto> {
}
