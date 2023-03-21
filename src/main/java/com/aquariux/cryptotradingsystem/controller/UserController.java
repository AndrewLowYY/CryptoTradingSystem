package com.aquariux.cryptotradingsystem.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aquariux.cryptotradingsystem.CryptoTradingSystemApplication;
import com.aquariux.cryptotradingsystem.model.Wallet;
import com.aquariux.cryptotradingsystem.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private static final Logger logger = LogManager.getLogger(CryptoTradingSystemApplication.class);
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/wallets/{userId}")
	public ResponseEntity<Map<String, Object>> getUserWallets(@PathVariable("userId") long userId) {
		
		logger.info("Retrieving wallet details for user id: " + userId);
		
		Map<String, Object> result = new HashMap<>();
		
		Set<Wallet> wallets = null;
		
		try {
			wallets = userService.getWalletsByUserId(userId);
		} catch (NoSuchElementException e) {
			logger.error("User with userId: " + userId + " not found.");
			result.put("status", "failure");
			result.put("message", "User not found");
			return new ResponseEntity<Map<String,Object>>(result, HttpStatus.BAD_REQUEST);
		}
		
		result.put("data", wallets);
		result.put("status", "success");
		
		return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
		
	}
	
}
