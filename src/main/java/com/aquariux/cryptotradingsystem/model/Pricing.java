package com.aquariux.cryptotradingsystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "Pricing")
public class Pricing {
	@Id
	@Column(name = "PricingID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long pricingID;
	
	@Column(name = "TradingPair")
	private String tradingPair;
	
	@Column(name = "AskPrice")
	private double askprice;
	
	@Column(name = "BidPrice")
	private double bidPrice;
	
	public Pricing() {
		
	}

	public Pricing(String tradingPair, double askprice, double bidPrice) {
		super();
		this.tradingPair = tradingPair;
		this.askprice = askprice;
		this.bidPrice = bidPrice;
	}
	
	public Long getPricingID() {
		return pricingID;
	}
	
	public void setPricingID(Long pricingID) {
		this.pricingID = pricingID;
	}

	public String getTradingPair() {
		return tradingPair;
	}

	public void setTradingPair(String tradingPair) {
		this.tradingPair = tradingPair;
	}

	public double getAskprice() {
		return askprice;
	}

	public void setAskPrice(double askprice) {
		this.askprice = askprice;
	}

	public double getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(double bidPrice) {
		this.bidPrice = bidPrice;
	}
}
