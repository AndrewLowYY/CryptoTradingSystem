package com.aquariux.cryptotradingsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.aquariux.cryptotradingsystem.model.User;
import com.aquariux.cryptotradingsystem.model.Wallet;
import com.aquariux.cryptotradingsystem.repository.UserRepository;
import com.aquariux.cryptotradingsystem.repository.WalletRepository;

@SpringBootApplication
@EnableScheduling
public class CryptoTradingSystemApplication implements ApplicationRunner{

	public static void main(String[] args) {
		SpringApplication.run(CryptoTradingSystemApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private WalletRepository walletRepository;
	
	// Load initial user with 50000 USDT in wallet
	@Override
	public void run(ApplicationArguments args) throws Exception {
		User user = userRepository.save(new User("Andrew", "Low", "andrewl"));
		walletRepository.save(new Wallet(user, "USDT", 50000.0));
	}
	
}