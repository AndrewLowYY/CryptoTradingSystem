package com.aquariux.cryptotradingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aquariux.cryptotradingsystem.model.User;
import com.aquariux.cryptotradingsystem.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
	
	public Wallet findByUserAndCryptoCurrency(User user, String cryptoCurrency);
	
}
