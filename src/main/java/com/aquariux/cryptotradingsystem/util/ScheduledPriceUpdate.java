package com.aquariux.cryptotradingsystem.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aquariux.cryptotradingsystem.model.Pricing;
import com.aquariux.cryptotradingsystem.repository.PricingRepository;

@Component
public class ScheduledPriceUpdate {
	
	private static final Logger logger = LogManager.getLogger(ScheduledPriceUpdate.class);
	
	@Autowired
	private PricingRepository pricingRepository;
	
	private JSONObject binanceEth;
	private JSONObject binanceBtc;
	private JSONObject huobiEth;
	private JSONObject huobiBtc;
	
	@Scheduled(fixedRate = 10000)
	public void loadPrices() {
		logger.info("Updating prices");
		binanceEth = null;
		binanceBtc = null;
		huobiEth = null;
		huobiBtc = null;
		
		loadBinance();
		loadHuobi();		
		updatePrices();
		logger.info("Prices updated");
	}
	
	private void loadBinance() {
		URL url;
		try {
			url = new URL("https://api.binance.com/api/v3/ticker/bookTicker");
		} catch (MalformedURLException e1) {
			logger.error("Error encountered with binance api url");
			return;
		}

		HttpURLConnection conn;
		int responsecode = -1;
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			//Getting the response code
			responsecode = conn.getResponseCode();
		} catch (IOException e) {
			logger.error("Error connecting to Binance API");
			return;
		}
		
		logger.debug("Binance connection response: " + responsecode);
		if (responsecode != 200) {
		    logger.error("Error encountered after Binance API connection");
		    return;
		} else {
		  
		    StringBuilder inline = new StringBuilder();
		    Scanner scanner;
			try {
				scanner = new Scanner(url.openStream());
			} catch (IOException e) {
				logger.error("Error opening Binance API response");
				return;
			}
		  
		   //Write all the JSON data into a string using a scanner
		    while (scanner.hasNext()) {
		       inline.append(scanner.nextLine());
		    }
		    
		    //Close the scanner
		    scanner.close();

		    //Using the JSON simple library parse the string into a json object
		    JSONArray array = new JSONArray(inline.toString());

		    Map<String, JSONObject> map = new HashMap<>();
		    
		    for (int i = 0; i < array.length(); i++) {
		    	JSONObject obj = array.getJSONObject(i);
		    	map.put(obj.getString("symbol"), obj);
		    }
		    binanceBtc = map.get("BTCUSDT");
		    binanceEth = map.get("ETHUSDT");
		    
		}
	}
	
	private void loadHuobi() {
		URL url;
		try {
			url = new URL("https://api.huobi.pro/market/tickers");
		} catch (MalformedURLException e) {
			logger.error("Error encountered with huobi api url");
			return;
		}

		HttpURLConnection conn;
		int responsecode = -1;
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			//Getting the response code
			responsecode = conn.getResponseCode();
		} catch (IOException e) {
			logger.error("Error connecting to Binance API");
			return;
		}
		
		logger.debug("Huobi connection response: " + responsecode);
		if (responsecode != 200) {
			logger.error("Error encountered after Binance API connection");
		    return;
		} else {
		    StringBuilder inline = new StringBuilder();
		    Scanner scanner;
		    try {
				scanner = new Scanner(url.openStream());
			} catch (IOException e) {
				logger.error("Error opening Binance API response");
				return;
			}		  
		   //Write all the JSON data into a string using a scanner
		    while (scanner.hasNext()) {
		       inline.append(scanner.nextLine());
		    }
		    
		    //Close the scanner
		    scanner.close();

		    //Using the JSON simple library parse the string into a json object
		    JSONObject data = new JSONObject(inline.toString());
		    
		    JSONArray array = data.getJSONArray("data");
		    Map<String, JSONObject> map = new HashMap<>();
		    
		    for (int i = 0; i < array.length(); i++) {
		    	JSONObject obj = array.getJSONObject(i);
		    	map.put(obj.getString("symbol"), obj);
		    }
		    
		    huobiEth = map.get("ethusdt");
		    huobiBtc = map.get("btcusdt");
		}
	}
	
	private void updatePrices() {
		
		Pricing ethUsdtPricing = pricingRepository.findByTradingPair("ETHUSDT");
		Pricing btcUsdtPricing = pricingRepository.findByTradingPair("BTCUSDT");
		
		if (btcUsdtPricing == null) {
			btcUsdtPricing = new Pricing();
			btcUsdtPricing.setTradingPair("BTCUSDT");
		}
		
		if (binanceBtc == null) {
			btcUsdtPricing.setAskPrice(huobiBtc.getDouble("ask"));
			btcUsdtPricing.setBidPrice(huobiBtc.getDouble("bid"));
		} else if (huobiBtc == null) {
			btcUsdtPricing.setAskPrice(binanceBtc.getDouble("askPrice"));
			btcUsdtPricing.setBidPrice(binanceBtc.getDouble("bidPrice"));
		} else {
			btcUsdtPricing.setAskPrice(Math.min(huobiBtc.getDouble("ask"), binanceBtc.getDouble("askPrice")));
			btcUsdtPricing.setBidPrice(Math.max(huobiBtc.getDouble("bid"), binanceBtc.getDouble("bidPrice")));
		}
		
		if (ethUsdtPricing == null) {
			ethUsdtPricing = new Pricing();
			ethUsdtPricing.setTradingPair("ETHUSDT");
		}
		
		if (binanceEth == null) {
			ethUsdtPricing.setAskPrice(huobiEth.getDouble("ask"));
			ethUsdtPricing.setBidPrice(huobiEth.getDouble("bid"));
		} else if (huobiEth == null) {
			ethUsdtPricing.setAskPrice(binanceEth.getDouble("askPrice"));
			ethUsdtPricing.setBidPrice(binanceEth.getDouble("bidPrice"));
		} else {
			ethUsdtPricing.setAskPrice(Math.min(huobiEth.getDouble("ask"), binanceEth.getDouble("askPrice")));
			ethUsdtPricing.setBidPrice(Math.max(huobiEth.getDouble("bid"), binanceEth.getDouble("bidPrice")));
		}
		
		pricingRepository.save(ethUsdtPricing);
		pricingRepository.save(btcUsdtPricing);
		
	}
}
