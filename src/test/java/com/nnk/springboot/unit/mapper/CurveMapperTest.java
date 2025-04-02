package com.nnk.springboot.unit.mapper;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.mapper.CurveMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * CurveMapper unit tests.
 */
@ExtendWith(MockitoExtension.class)
public class CurveMapperTest {

    private static CurveMapper curveMapper;

    private CurvePointDto curvePointDto;
    private CurvePoint curvePoint;

    /**
     * Sets up for all tests.
     */
    @BeforeAll
    public static void setUp() {
        curveMapper = Mappers.getMapper(CurveMapper.class);
    }

    /**
     * Sets up before each test.
     */
    @BeforeEach
    public void beforeEach() {
        curvePointDto = new CurvePointDto();
        curvePointDto.setCurveId(127);
        curvePointDto.setId(1);
        curvePointDto.setTerm(1d);
        curvePointDto.setValue(1d);

        curvePoint = new CurvePoint();
        curvePoint.setCurveId(127);
        curvePoint.setId(1);
        curvePoint.setTerm(1d);
        curvePoint.setValue(1d);
    }

    /**
     * Test toDto
     * Given: A CurvePoint
     * When: toDto
     * Then: Return a CurvePointDto
     */
    @Test
    public void givenCurvePoint_whenToDto_thenReturnCurvePointDto() {

        // When
        CurvePointDto curvePointDtoActual = curveMapper.toDto(curvePoint);

        // Then
        assertEquals(curvePointDto.getCurveId(), curvePointDtoActual.getCurveId());
        assertEquals(curvePoint.getId(), curvePointDtoActual.getId());
        assertEquals(curvePoint.getTerm(), curvePointDtoActual.getTerm());
        assertEquals(curvePoint.getValue(), curvePointDtoActual.getValue());

    }


    /**
     * Test toDto
     * Given: A null CurvePoint
     * When: toDto
     * Then: Return null
     */
    @Test
    public void givenNull_whenToDto_thenReturnNull() {

        // When
        CurvePointDto curvePointDtoActual = curveMapper.toDto(null);

        // Then
        assertNull(curvePointDtoActual);
    }

    /**
     * Test toDomain
     * Given: A CurvePointDto
     * When: toDomain
     * Then: Return a CurvePoint
     */
    @Test
    public void givenCurveCurvePointDto_whenToDomain_thenReturnCurvePoint() {

        // When
        CurvePoint curvePointActual = curveMapper.toDomain(curvePointDto);

        // Then
        assertEquals(curvePoint.getCurveId(), curvePointActual.getCurveId());
        assertEquals(curvePoint.getId(), curvePointActual.getId());
        assertEquals(curvePoint.getTerm(), curvePointActual.getTerm());
        assertEquals(curvePoint.getValue(), curvePointActual.getValue());

    }


    /**
     * Test toDomain
     * Given: A null CurvePointDto
     * When: toDomain
     * Then: Return null
     */
    @Test
    public void givenNull_whenToDomain_thenReturnNull() {

        // When
        CurvePoint curvePointActual = curveMapper.toDomain(null);

        // Then
        assertNull(curvePointActual);
    }

}
