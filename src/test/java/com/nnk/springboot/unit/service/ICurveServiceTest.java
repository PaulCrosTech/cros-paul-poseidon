package com.nnk.springboot.unit.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.exceptions.EntityMissingException;
import com.nnk.springboot.mapper.CurveMapper;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.ICurveService;
import com.nnk.springboot.service.impl.CurveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * The ICurveServiceTest unit test
 */
@ExtendWith(MockitoExtension.class)
public class ICurveServiceTest {

    @Mock
    private CurveMapper curveMapper;
    @Mock
    private CurvePointRepository curvePointRepository;

    private ICurveService curveService;

    /**
     * Setup before each test
     */
    @BeforeEach
    public void setUp() {
        curveService = new CurveService(curveMapper, curvePointRepository);
    }

    /**
     * Test findAll
     * Given: A list of CurvePoint
     * When: findAll
     * Then: Return the CurvePointDto list
     */
    @Test
    public void givenCurvePointList_whenFindAll_thenReturnCurvePointDtoList() {
        // Given
        when(curvePointRepository.findAll()).thenReturn(new ArrayList<>());

        // When
        List<CurvePointDto> curvePointDtoList = curveService.findAll();

        // Then
        assertEquals(new ArrayList<>(), curvePointDtoList);
    }

    /**
     * Test findById
     * Given: A CurvePoint id
     * When: findById
     * Then: Return the CurvePointDto
     */
    @Test
    public void givenExistingCurvePointId_whenFindById_thenReturnCurvePointDto() {
        // Given
        CurvePointDto curvePointDto = new CurvePointDto();
        curvePointDto.setId(1);
        curvePointDto.setTerm(10d);
        curvePointDto.setValue(5d);

        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(new CurvePoint()));
        when(curveMapper.toCurvePointDto(any(CurvePoint.class))).thenReturn(curvePointDto);

        // When
        CurvePointDto curvePointDtoActual = curveService.findById(anyInt());

        // Then
        assertEquals(curvePointDto, curvePointDtoActual);
    }

    /**
     * Test findById
     * Given: A non-existing CurvePoint id
     * When: findById
     * Then: Throw EntityMissingException
     */
    @Test
    public void givenNonExistingCurvePointId_whenFindById_thenThrowEntityMissingException() {
        // Given
        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityMissingException.class,
                () -> curveService.findById(anyInt())
        );
    }

    /**
     * Test save
     * Given: A CurvePointDto
     * When: save
     * Then: The CurvePoint is saved
     */
    @Test
    public void givenCurvePointDto_whenSave_thenCurvePointSaved() {
        // Given
        CurvePointDto curvePointDto = new CurvePointDto();
        CurvePoint curvePoint = new CurvePoint();

        when(curveMapper.toCurvePoint(curvePointDto)).thenReturn(curvePoint);

        // When
        curveService.create(curvePointDto);

        // Then
        verify(curvePointRepository, times(1)).save(curvePoint);
    }

    /**
     * Test update
     * Given: A CurvePointDto with existing Id
     * When: update
     * Then: CurvePoint is updated
     */
    @Test
    public void givenExistingCurvePointDto_whenUpdate_thenCurvePointIsUpdated() {
        // Given
        CurvePointDto curvePointDto = new CurvePointDto();
        curvePointDto.setId(1);

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);

        when(curvePointRepository.findById(curvePointDto.getId())).thenReturn(Optional.of(curvePoint));

        // When
        curveService.update(curvePointDto);

        // Then
        verify(curvePointRepository, times(1)).save(any(CurvePoint.class));
    }

    /**
     * Test update
     * Given: A CurvePointDto with non-existing Id
     * When: update
     * Then: Throw EntityMissingException
     */
    @Test
    public void givenNonExistingCurvePointDto_whenUpdate_thenThrowEntityMissingException() {
        // Given
        CurvePointDto curvePointDto = new CurvePointDto();
        curvePointDto.setId(1);

        when(curvePointRepository.findById(curvePointDto.getId())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityMissingException.class,
                () -> curveService.update(curvePointDto)
        );
        verify(curvePointRepository, times(0)).save(any(CurvePoint.class));
    }

    @Test
    public void givenExistingCurvePointId_whenDelete_thenCurvePoointIsDeleted() {
        // Given
        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(new CurvePoint()));

        // When
        curveService.delete(anyInt());

        // Then
        verify(curvePointRepository, times(1)).delete(any(CurvePoint.class));
    }

    /**
     * Test delete
     * Given: A non-existing CurvePoint id
     * When: delete
     * Then: Throw EntityMissingException
     */
    @Test
    public void givenNonExistingCurvePointId_whenDelete_thenThrowEntityMissingException() {
        // Given
        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityMissingException.class,
                () -> curveService.delete(anyInt())
        );

    }

}
