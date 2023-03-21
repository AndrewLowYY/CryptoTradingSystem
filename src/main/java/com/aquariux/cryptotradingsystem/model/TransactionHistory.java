package com.aquariux.cryptotradingsystem.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "TransactionHistory")
public class TransactionHistory {
	
	@Id
	@Column(name = "TransactionID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long transactionId;
	
	@ManyToOne
	@JoinColumn(name = "UserID", nullable=false)
	@JsonIgnore
	private User user;
	
	@Column(name = "TransactionDate")
	private Date transactionDate;
	
	@Column(name = "SoldCurrency")
	private String soldCurrency;
	
	@Column(name = "BoughtCurrency")
	private String boughtCurrency;
	
	@Column(name = "AmountSold")
	private double amountSold;
	
	@Column(name = "AmountBought")
	private double amountBought;

	public TransactionHistory() {
		
	}
	
	public TransactionHistory(User user, Date transactionDate, String soldCurrency, String boughtCurrency,
			double amountSold, double amountBought) {
		super();
		this.user = user;
		this.transactionDate = transactionDate;
		this.soldCurrency = soldCurrency;
		this.boughtCurrency = boughtCurrency;
		this.amountSold = amountSold;
		this.amountBought = amountBought;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getSoldCurrency() {
		return soldCurrency;
	}

	public void setSoldCurrency(String soldCurrency) {
		this.soldCurrency = soldCurrency;
	}

	public String getBoughtCurrency() {
		return boughtCurrency;
	}

	public void setBoughtCurrency(String boughtCurrency) {
		this.boughtCurrency = boughtCurrency;
	}

	public double getAmountSold() {
		return amountSold;
	}

	public void setAmountSold(double amountSold) {
		this.amountSold = amountSold;
	}

	public double getAmountBought() {
		return amountBought;
	}

	public void setAmountBought(double amountBought) {
		this.amountBought = amountBought;
	}

	@Override
	public String toString() {
		return "TransactionHistory [transactionId=" + transactionId + ", transactionDate="
				+ transactionDate + ", soldCurrency=" + soldCurrency + ", boughtCurrency=" + boughtCurrency
				+ ", amountSold=" + amountSold + ", amountBought=" + amountBought + "]";
	}
	
}
