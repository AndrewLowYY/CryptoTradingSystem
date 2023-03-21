package com.aquariux.cryptotradingsystem.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.aquariux.cryptotradingsystem.model.User;
import com.aquariux.cryptotradingsystem.model.Wallet;

@Service
public interface UserService {
	
	public Optional<User> getUserById(long userId);

	Set<Wallet> getWalletsByUserId(long userId);
	
}
