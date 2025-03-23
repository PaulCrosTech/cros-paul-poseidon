package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * TradeRepository
 */
public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
