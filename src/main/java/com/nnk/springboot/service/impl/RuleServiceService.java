package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.dto.RuleDto;
import com.nnk.springboot.exceptions.EntityMissingException;
import com.nnk.springboot.mapper.RuleMapper;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.IRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * The Rule service
 */
@Service
@Slf4j
public class RuleServiceService implements IRuleService {

    private final RuleNameRepository ruleNameRepository;
    private final RuleMapper ruleMapper;

    /**
     * Constructor
     *
     * @param ruleNameRepository the ruleNameRepository
     * @param ruleMapper         the ruleMapper
     */
    public RuleServiceService(RuleNameRepository ruleNameRepository, RuleMapper ruleMapper) {
        this.ruleNameRepository = ruleNameRepository;
        this.ruleMapper = ruleMapper;
    }


    /**
     * Find all rules
     *
     * @return the list of rules
     */
    @Override
    public List<RuleDto> findAll() {
        List<Rule> rules = ruleNameRepository.findAll();
        List<RuleDto> ruleDtoList = new ArrayList<>();

        rules.forEach(rule -> {
            ruleDtoList.add(ruleMapper.toRuleDto(rule));
        });

        return ruleDtoList;
    }

    /**
     * Find rule by id
     *
     * @param id the id of the rule to find
     * @return the rule
     * @throws EntityMissingException the rule not found exception
     */
    @Override
    public RuleDto findById(Integer id) throws EntityMissingException {
        log.debug("====> rule by id {} <====", id);
        Rule rule = ruleNameRepository.findById(id)
                .orElseThrow(() -> new EntityMissingException("Rule not found with id : " + id));
        return ruleMapper.toRuleDto(rule);
    }

    /**
     * Create a rule in the database
     *
     * @param ruleDto the rule to add
     */
    @Transactional
    @Override
    public void create(RuleDto ruleDto) {
        log.debug("====> creating a new rule <====");

        ruleNameRepository.save(ruleMapper.toRule(ruleDto));

        log.debug("====> rule created <====");
    }

    /**
     * Update a rule in the database
     *
     * @param ruleDto the rule to update
     * @throws EntityMissingException the rule not found exception
     */
    @Transactional
    @Override
    public void update(RuleDto ruleDto) throws EntityMissingException {
        log.debug("====> rule with id {} <====", ruleDto.getId());

        Rule rule = ruleNameRepository.findById(ruleDto.getId())
                .orElseThrow(
                        () -> new EntityMissingException("Rule not found with id : " + ruleDto.getId())
                );

        rule.setName(ruleDto.getName());
        rule.setDescription(ruleDto.getDescription());
        rule.setJson(ruleDto.getJson());
        rule.setTemplate(ruleDto.getTemplate());
        rule.setSqlStr(ruleDto.getSqlStr());
        rule.setSqlPart(ruleDto.getSqlPart());

        ruleNameRepository.save(rule);
        log.debug("====> rule updated <====");
    }

    /**
     * Delete a rule in the database
     *
     * @param id the id of the rule to delete
     * @throws EntityMissingException the rule not found exception
     */
    @Transactional
    @Override
    public void delete(Integer id) throws EntityMissingException {
        Rule rule = ruleNameRepository.findById(id)
                .orElseThrow(
                        () -> new EntityMissingException("Rule not found with id : " + id)
                );
        ruleNameRepository.delete(rule);
    }
}
