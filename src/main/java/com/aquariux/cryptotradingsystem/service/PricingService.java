package com.aquariux.cryptotradingsystem.service;

import org.springframework.stereotype.Service;

import com.aquariux.cryptotradingsystem.model.Pricing;

@Service
public interface PricingService {
	
	public Pricing getBestPrices(String tradingPair);
	
}
