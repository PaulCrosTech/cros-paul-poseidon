package com.nnk.springboot.service;

import com.nnk.springboot.dto.RuleDto;
import com.nnk.springboot.exceptions.EntityMissingException;

import java.util.List;

/**
 * The IRule service interface
 */
public interface IRuleService {

    /**
     * Find all rules
     *
     * @return the list of rules
     */
    List<RuleDto> findAll();

    /**
     * Find rule by id
     *
     * @param id the id of the rule to find
     * @return the rule
     * @throws EntityMissingException the rule not found exception
     */
    RuleDto findById(Integer id) throws EntityMissingException;

    /**
     * Create a rule in the database
     *
     * @param ruleDto the rule to add
     */
    void create(RuleDto ruleDto);

    /**
     * Update a rule in the database
     *
     * @param ruleDto the rule to update
     * @throws EntityMissingException the rule not found exception
     */
    void update(RuleDto ruleDto) throws EntityMissingException;

    /**
     * Delete a rule in the database
     *
     * @param id the id of the rule to delete
     * @throws EntityMissingException the rule not found exception
     */
    void delete(Integer id) throws EntityMissingException;
}
