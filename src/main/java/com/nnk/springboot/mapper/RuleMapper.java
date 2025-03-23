package com.nnk.springboot.mapper;


import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.dto.RuleDto;
import org.mapstruct.Mapper;

/**
 * RuleMapper is used to convert Rule and RuleDto objects
 */
@Mapper(componentModel = "spring")
public interface RuleMapper {

    /**
     * Convert a RuleDto to a Rule
     *
     * @param ruleDto the RuleDto to convert
     * @return the Rule
     */
    Rule toRule(RuleDto ruleDto);

    /**
     * Convert a Rule to a RuleDto
     *
     * @param rule the Rule to convert
     * @return the RuleDto
     */
    RuleDto toRuleDto(Rule rule);

}
