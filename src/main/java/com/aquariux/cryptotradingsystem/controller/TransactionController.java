package com.aquariux.cryptotradingsystem.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aquariux.cryptotradingsystem.exceptions.InsufficientBalanceException;
import com.aquariux.cryptotradingsystem.exceptions.InvalidTradingPairException;
import com.aquariux.cryptotradingsystem.exceptions.LockAcquisitionException;
import com.aquariux.cryptotradingsystem.exceptions.NegativeTradeException;
import com.aquariux.cryptotradingsystem.model.TransactionModel;
import com.aquariux.cryptotradingsystem.model.User;
import com.aquariux.cryptotradingsystem.service.TransactionService;
import com.aquariux.cryptotradingsystem.service.UserService;

@RestController
@RequestMapping("/transact")
public class TransactionController {

	private static final Logger logger = LogManager.getLogger(TransactionController.class);	
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> submitTransaction(@RequestBody TransactionModel transactionDetails) {
		
		Map<String, Object> result = new HashMap<>();
		
		// Check if user exists
		User user = null;
		try {
			user = userService.getUserById(transactionDetails.getUserId()).get();
		} catch (NoSuchElementException e) {
			logger.error("User: " + transactionDetails.getUserId() + " not found");
			result.put("message", "User not found");
			return new ResponseEntity<Map<String,Object>>(result, HttpStatus.BAD_REQUEST);
		}
		
		try {
			transactionService.submitTransaction(transactionDetails, user);
		} catch (LockAcquisitionException e) {
			result.put("message", "Unable to obtain lock on user's wallets");
			return new ResponseEntity<Map<String,Object>>(result, HttpStatus.BAD_REQUEST);
		} catch (InvalidTradingPairException e) {
			result.put("message", "Invalid trading pair");
			return new ResponseEntity<Map<String,Object>>(result, HttpStatus.BAD_REQUEST);
		} catch (NegativeTradeException e) {
			result.put("message", "Invalid trading amount");
			return new ResponseEntity<Map<String,Object>>(result, HttpStatus.BAD_REQUEST);
		} catch (InsufficientBalanceException e) {
			result.put("message", "Insufficient balance in deducting wallet");
			return new ResponseEntity<Map<String,Object>>(result, HttpStatus.BAD_REQUEST);
		} finally {
			userService.releaseLock(user);
		}
		
		logger.info("Trade submitted successfully");
		
		result.put("status", "success");
		result.put("message", "Trade submitted successfully");
		
		return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
	}
}
