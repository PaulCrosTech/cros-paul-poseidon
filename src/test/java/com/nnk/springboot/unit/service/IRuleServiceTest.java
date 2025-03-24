package com.nnk.springboot.unit.service;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.dto.RuleDto;
import com.nnk.springboot.exceptions.EntityMissingException;
import com.nnk.springboot.mapper.RuleMapper;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.IRuleService;
import com.nnk.springboot.service.impl.RuleService;
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
 * The IRuleServiceTest unit test
 */
@ExtendWith(MockitoExtension.class)
public class IRuleServiceTest {


    @Mock
    private RuleNameRepository ruleNameRepository;
    @Mock
    private RuleMapper ruleMapper;

    private IRuleService ruleService;

    /**
     * Setup before each test
     */
    @BeforeEach
    public void setUp() {
        ruleService = new RuleService(ruleNameRepository, ruleMapper);
    }


    /**
     * Test findAll
     * Given: A list of Rule
     * When: findAll
     * Then: Return the RuleDto list
     */
    @Test
    public void givenBidList_whenFindAll_thenReturnBidListDto() {
        // Given
        when(ruleNameRepository.findAll()).thenReturn(new ArrayList<>());

        // When
        List<RuleDto> ruleDtoList = ruleService.findAll();

        // Then
        assertEquals(new ArrayList<>(), ruleDtoList);
    }


    /**
     * Test findById
     * Given: A Rule
     * When: findById
     * Then: Return the RuleDto
     */
    @Test
    public void givenExistingRuleId_whenFindById_thenReturnRuleDto() {
        // Given
        RuleDto ruleDtoExpected = new RuleDto();
        ruleDtoExpected.setId(1);
        ruleDtoExpected.setJson("json");
        ruleDtoExpected.setName("name");
        ruleDtoExpected.setDescription("description");
        ruleDtoExpected.setTemplate("template");
        ruleDtoExpected.setSqlPart("sqlPart");
        ruleDtoExpected.setSqlStr("sqlStr");

        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.of(new Rule()));
        when(ruleMapper.toRuleDto(any(Rule.class))).thenReturn(ruleDtoExpected);

        // When
        RuleDto ruleDtoActual = ruleService.findById(anyInt());

        // Then
        assertEquals(ruleDtoExpected, ruleDtoActual);
    }

    /**
     * Test findById
     * Given: A non existing Rule Id
     * When: findById
     * Then: Throw EntityMissingException
     */
    @Test
    public void givenNonExistingRuleId_whenFindById_thenThrowEntityMissingException() {
        // Given
        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityMissingException.class,
                () -> ruleService.findById(anyInt())
        );
    }

    /**
     * Test create
     * Given: A RuleDto
     * When: create
     * Then: Rule is saved
     */
    @Test
    public void givenRuleDto_whenSave_thenRuleIsSaved() {
        // Given
        RuleDto ruleDto = new RuleDto();
        Rule rule = new Rule();

        when(ruleMapper.toRule(ruleDto)).thenReturn(rule);

        // When
        ruleService.create(ruleDto);

        // Then
        verify(ruleNameRepository, times(1)).save(rule);
    }

    /**
     * Test update
     * Given: A RuleDto with existing Id
     * When: update
     * Then: Rule is updated
     */
    @Test
    public void givenExistingRuleDto_whenUpdate_thenRuleIsUpdated() {
        // Given
        RuleDto ruleDto = new RuleDto();
        ruleDto.setId(1);

        Rule rule = new Rule();
        rule.setId(1);

        when(ruleNameRepository.findById(ruleDto.getId())).thenReturn(Optional.of(rule));

        // When
        ruleService.update(ruleDto);

        // Then
        verify(ruleNameRepository, times(1)).save(any(Rule.class));
    }

    /**
     * Test update
     * Given: A non existing RuleDto
     * When: update
     * Then: Throw EntityMissingException
     */
    @Test
    public void givenNonExistingRuleDto_whenUpdate_thenThrowEntityMissingException() {
        // Given
        RuleDto ruleDto = new RuleDto();
        ruleDto.setId(1);

        when(ruleNameRepository.findById(ruleDto.getId())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityMissingException.class,
                () -> ruleService.update(ruleDto)
        );
        verify(ruleNameRepository, times(0)).save(any(Rule.class));
    }

    /**
     * Test delete
     * Given: A Rule Id
     * When: delete
     * Then: Rule is deleted
     */
    @Test
    public void givenExistingBidId_whenDelete_thenBidIsDeleted() {
        // Given
        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.of(new Rule()));

        // When
        ruleService.delete(anyInt());

        // Then
        verify(ruleNameRepository, times(1)).delete(any(Rule.class));
    }

    /**
     * Test delete
     * Given: A non existing Rule Id
     * When: delete
     * Then: Throw EntityMissingException
     */
    @Test
    public void givenNonExistingRuleId_whenDelete_thenThrowEntityMissingException() {
        // Given
        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityMissingException.class,
                () -> ruleService.delete(anyInt())
        );
    }

}
