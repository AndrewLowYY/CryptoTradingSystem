package com.aquariux.cryptotradingsystem.service.impl;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aquariux.cryptotradingsystem.exceptions.InsufficientBalanceException;
import com.aquariux.cryptotradingsystem.exceptions.InvalidTradingPairException;
import com.aquariux.cryptotradingsystem.exceptions.LockAcquisitionException;
import com.aquariux.cryptotradingsystem.exceptions.NegativeTradeException;
import com.aquariux.cryptotradingsystem.model.Pricing;
import com.aquariux.cryptotradingsystem.model.TransactionHistory;
import com.aquariux.cryptotradingsystem.model.TransactionModel;
import com.aquariux.cryptotradingsystem.model.User;
import com.aquariux.cryptotradingsystem.model.Wallet;
import com.aquariux.cryptotradingsystem.repository.PricingRepository;
import com.aquariux.cryptotradingsystem.repository.TransactionHistoryRepository;
import com.aquariux.cryptotradingsystem.repository.WalletRepository;
import com.aquariux.cryptotradingsystem.service.TransactionService;
import com.aquariux.cryptotradingsystem.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	private static final Logger logger = LogManager.getLogger(TransactionServiceImpl.class);
	
	@Autowired
	private PricingRepository pricingRepository;
	
	@Autowired
	private WalletRepository walletRepository;
	
	@Autowired
	private TransactionHistoryRepository transactionHistoryRepository;
	
	@Autowired
	private UserService userService;
	
	@Transactional
	@Override
	public void submitTransaction(TransactionModel transactionDetails, User user) throws InvalidTradingPairException, LockAcquisitionException, NegativeTradeException, InsufficientBalanceException {
		
		logger.info("Submitting transaction");
		// Obtain lock on user's wallets
		if (!userService.obtainLock(user)) {
			throw new LockAcquisitionException();
		}
		
		// Check if trading pair exists
		String buying = transactionDetails.getBuying();
		String selling = transactionDetails.getSelling();
		
		// User is buying
		String tradingPair = buying + selling;
		Pricing pricing = pricingRepository.findByTradingPair(tradingPair);
		double price = -1.0;
		if (pricing != null) {
			price = 1.0 / pricing.getAskprice();
		}
		
		// User is selling
		if (pricing == null) {
			tradingPair = selling + buying;
			pricing = pricingRepository.findByTradingPair(tradingPair);
			
			if (pricing == null) {
				logger.error("Invalid trading pair: " + tradingPair);
				throw new InvalidTradingPairException();
			}
			
			price = pricing.getBidPrice();
		}
		
		double deductingAmount = transactionDetails.getAmount();
		if (deductingAmount < 0) {
			// User sells negative amount
			logger.error("Invalid selling amount");
			throw new NegativeTradeException();
		}
		
		// Get selling and buying wallets
		Wallet deductingWallet = walletRepository.findByUserAndCryptoCurrency(user, selling);
		Wallet addingWallet = walletRepository.findByUserAndCryptoCurrency(user, buying);
		if (deductingWallet == null || deductingWallet.getAmount() < deductingAmount) {
			logger.error("Insufficient balance in wallet");
			throw new InsufficientBalanceException();
		}
		
		if (addingWallet == null) {
			addingWallet = walletRepository.save(new  Wallet(user, buying, 0.0));
		}
		
		deductingWallet.setAmount(deductingWallet.getAmount() - deductingAmount);
		addingWallet.setAmount(addingWallet.getAmount() + (deductingAmount * price));
		
		// deduct from selling, add to buying
		walletRepository.save(deductingWallet);
		walletRepository.save(addingWallet);
		
		transactionHistoryRepository.save(new TransactionHistory(user, new Date(), selling, buying, deductingAmount, deductingAmount * price));
		
	}
}
