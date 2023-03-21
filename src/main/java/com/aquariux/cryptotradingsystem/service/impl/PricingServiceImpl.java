package com.aquariux.cryptotradingsystem.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aquariux.cryptotradingsystem.controller.PricingController;
import com.aquariux.cryptotradingsystem.model.Pricing;
import com.aquariux.cryptotradingsystem.repository.PricingRepository;
import com.aquariux.cryptotradingsystem.service.PricingService;

@Service
public class PricingServiceImpl implements PricingService {

	private static final Logger logger = LogManager.getLogger(PricingController.class);
	
	@Autowired
	PricingRepository pricingRepository;
	
	@Override
	public Pricing getBestPrices(String tradingPair) {
		return pricingRepository.findByTradingPair(tradingPair);
	}
	
}
