package com.aquariux.cryptotradingsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "Wallet")
public class Wallet {
	
	@Id
	@Column(name = "WalletID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long walletId;
	
	@ManyToOne
	@JoinColumn(name = "UserID", nullable=false)
	@JsonIgnore
	private User user;
	
	@Column(name = "CryptoCurrency")
	private String cryptoCurrency;
	
	@Column(name = "Amount")
	private double amount;

	public Wallet() {
		
	}
	
	public Wallet(User user, String cryptoCurrency, double amount) {
		super();
		this.user = user;
		this.cryptoCurrency = cryptoCurrency;
		this.amount = amount;
	}

	public Long getWalletId() {
		return walletId;
	}

	public void setWalletId(Long walletId) {
		this.walletId = walletId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCryptoCurrency() {
		return cryptoCurrency;
	}

	public void setCryptoCurrency(String cryptoCurrency) {
		this.cryptoCurrency = cryptoCurrency;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Wallet [walletId=" + walletId + ", cryptoCurrency=" + cryptoCurrency + ", amount="
				+ amount + "]";
	}
	
	
}
