package com.aquariux.cryptotradingsystem.service.impl;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aquariux.cryptotradingsystem.model.TransactionHistory;
import com.aquariux.cryptotradingsystem.model.User;
import com.aquariux.cryptotradingsystem.model.Wallet;
import com.aquariux.cryptotradingsystem.repository.UserRepository;
import com.aquariux.cryptotradingsystem.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Optional<User> getUserById(long userId) {
		
		return userRepository.findById(userId);
	}
	
	@Override
	public Set<Wallet> getWalletsByUserId(long userId) throws NoSuchElementException {
		
		Optional<User> userWrapper = userRepository.findById(userId);
		
		User user = userWrapper.get();
		
		return user.getWallets();
	}
	
	@Override
	public List<TransactionHistory> getTransactionHistoryByUserId(long userId) throws NoSuchElementException {
		Optional<User> userWrapper = userRepository.findById(userId);
		
		User user = userWrapper.get();
		
		return user.getTransactionHistory();
	}
	
	@Override
	public void releaseLock(User user) {
		user.setLock(false);
		userRepository.save(user);
		
	}
	
	@Override
	public boolean obtainLock(User user) {
		// Get lock on user wallets
		if (user.getLock() != null && user.getLock()) {
			// Another thread has a lock on user
			return false;
		}
		else {
			user.setLock(true);
			user.setLockedDate(new Date());
			userRepository.save(user);
			return true;
		}
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	

}
