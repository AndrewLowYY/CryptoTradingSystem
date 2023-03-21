package com.aquariux.cryptotradingsystem.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.aquariux.cryptotradingsystem.model.TransactionHistory;
import com.aquariux.cryptotradingsystem.model.User;
import com.aquariux.cryptotradingsystem.model.Wallet;

@Service
public interface UserService {
	
	public Optional<User> getUserById(long userId);

	public Set<Wallet> getWalletsByUserId(long userId);
	
	public List<TransactionHistory> getTransactionHistoryByUserId(long userId);
	
	public void releaseLock(User user);

	public boolean obtainLock(User user);
	
	public User save(User user);
	
}
