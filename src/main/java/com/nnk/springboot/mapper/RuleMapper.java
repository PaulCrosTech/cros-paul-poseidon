package com.nnk.springboot.mapper;


import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.dto.RuleDto;
import org.mapstruct.Mapper;

/**
 * RuleMapper is used to convert Rule and RuleDto objects
 */
@Mapper(componentModel = "spring")
public interface RuleMapper extends IMapper<Rule, RuleDto> {
}
