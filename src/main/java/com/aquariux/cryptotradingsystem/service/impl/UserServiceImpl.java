package com.aquariux.cryptotradingsystem.service.impl;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aquariux.cryptotradingsystem.model.User;
import com.aquariux.cryptotradingsystem.model.Wallet;
import com.aquariux.cryptotradingsystem.repository.UserRepository;
import com.aquariux.cryptotradingsystem.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	
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

}
