package com.aquariux.cryptotradingsystem.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aquariux.cryptotradingsystem.model.Pricing;
import com.aquariux.cryptotradingsystem.service.PricingService;

@RestController
@RequestMapping("/pricing")
public class PricingController {
	
	private static final Logger logger = LogManager.getLogger(PricingController.class);
	
	@Autowired
	private PricingService pricingService;
	
	@GetMapping(value = "/{tradingPair}")
	public ResponseEntity<Map<String, Object>> getBestPrices(@PathVariable("tradingPair") String tradingPair) {
		logger.info("Obtaining aggregated prices for trading pair: " + tradingPair);
		
		Map<String, Object> result = new HashMap<>();
		
		Pricing bestPrice = pricingService.getBestPrices(tradingPair);
		if (bestPrice != null) {
			result.put("status", "success");
			result.put("data", bestPrice);
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		}
		
		logger.error("Prices for trading pair " + tradingPair + " not found.");
		
		result.put("status", "failure");
		result.put("message", "Invalid trading pair");
		
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.BAD_REQUEST);
		
	}
}
