package com.nnk.springboot.unit.mapper;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.dto.RuleDto;
import com.nnk.springboot.mapper.RuleMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * RuleMapper unit tests.
 */
@ExtendWith(MockitoExtension.class)
public class RuleMapperTest {

    private static RuleMapper ruleMapper;

    private Rule rule;
    private RuleDto ruleDto;

    /**
     * Sets up for all tests.
     */
    @BeforeAll
    public static void setUp() {
        ruleMapper = Mappers.getMapper(RuleMapper.class);
    }

    /**
     * Sets up before each test.
     */
    @BeforeEach
    public void setUpPerTest() {

        rule = new Rule();
        rule.setId(1);
        rule.setName("name");
        rule.setDescription("description");
        rule.setJson("json");
        rule.setTemplate("template");
        rule.setSqlStr("sqlStr");
        rule.setSqlPart("sqlPart");

        ruleDto = new RuleDto();
        ruleDto.setId(1);
        ruleDto.setName("name");
        ruleDto.setDescription("description");
        ruleDto.setJson("json");
        ruleDto.setTemplate("template");
        ruleDto.setSqlStr("sqlStr");
        ruleDto.setSqlPart("sqlPart");
    }


    /**
     * Test toDomain
     * Given: A RuleDto
     * When: toDomain
     * Then: Return a Rule
     */
    @Test
    public void givenRuleDto_whentoDomain_thenReturnRule() {

        // When
        Rule ruleActual = ruleMapper.toDomain(ruleDto);

        // Then
        assertEquals(rule.getId(), ruleActual.getId());
        assertEquals(rule.getName(), ruleActual.getName());
        assertEquals(rule.getDescription(), ruleActual.getDescription());
        assertEquals(rule.getJson(), ruleActual.getJson());
        assertEquals(rule.getTemplate(), ruleActual.getTemplate());
        assertEquals(rule.getSqlStr(), ruleActual.getSqlStr());
        assertEquals(rule.getSqlPart(), ruleActual.getSqlPart());
    }

    /**
     * Test toDto
     * Given: A Rule
     * When: toDto
     * Then: Return a RuleDto
     */
    @Test
    public void givenRule_whentoDto_thenReturnRuleDto() {
        // When
        RuleDto ruleDtoActual = ruleMapper.toDto(rule);

        // Then
        assertEquals(ruleDto.getId(), ruleDtoActual.getId());
        assertEquals(ruleDto.getName(), ruleDtoActual.getName());
        assertEquals(ruleDto.getDescription(), ruleDtoActual.getDescription());
        assertEquals(ruleDto.getJson(), ruleDtoActual.getJson());
        assertEquals(ruleDto.getTemplate(), ruleDtoActual.getTemplate());
        assertEquals(ruleDto.getSqlStr(), ruleDtoActual.getSqlStr());
        assertEquals(ruleDto.getSqlPart(), ruleDtoActual.getSqlPart());

    }
}
