package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.dto.RuleDto;
import com.nnk.springboot.mapper.RuleMapper;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


/**
 * The Rule service
 */
@Service
@Slf4j
public class RuleService extends AbstractCrudService<Rule, RuleDto> {
    /**
     * Constructor
     *
     * @param mapper     the mapper
     * @param repository the repository
     */
    public RuleService(@Qualifier("ruleMapperImpl") RuleMapper mapper,
                       RuleNameRepository repository) {
        super(mapper, repository);
    }
}
