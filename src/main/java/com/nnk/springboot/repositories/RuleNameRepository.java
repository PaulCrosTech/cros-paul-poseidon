package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * RuleNameRepository
 */
public interface RuleNameRepository extends JpaRepository<Rule, Integer> {
}
