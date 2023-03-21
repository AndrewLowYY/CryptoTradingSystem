package com.aquariux.cryptotradingsystem.model;

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
	private int walletId;
	
	@ManyToOne
	@JoinColumn(name = "UserID", nullable=false)
	private User user;
	
	@Column(name = "Symbol")
	private String symbol;
	
	@Column(name = "Amount")
	private double amount;

	public Wallet() {
		
	}
	
	public Wallet(User user, String symbol, double amount) {
		super();
		this.user = user;
		this.symbol = symbol;
		this.amount = amount;
	}

	public int getWalletId() {
		return walletId;
	}

	public void setWalletId(int walletId) {
		this.walletId = walletId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
}
