package com.aquariux.cryptotradingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aquariux.cryptotradingsystem.model.Pricing;

public interface PricingRepository extends JpaRepository<Pricing, Long>{

	Pricing findByTradingPair(String tradingPair);
		
}
