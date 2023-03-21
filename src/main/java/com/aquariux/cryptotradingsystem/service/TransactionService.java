package com.aquariux.cryptotradingsystem.service;

import com.aquariux.cryptotradingsystem.exceptions.InsufficientBalanceException;
import com.aquariux.cryptotradingsystem.exceptions.InvalidTradingPairException;
import com.aquariux.cryptotradingsystem.exceptions.LockAcquisitionException;
import com.aquariux.cryptotradingsystem.exceptions.NegativeTradeException;
import com.aquariux.cryptotradingsystem.model.TransactionModel;
import com.aquariux.cryptotradingsystem.model.User;

public interface TransactionService {

	public void submitTransaction(TransactionModel transactionDetails, User user) throws InvalidTradingPairException, LockAcquisitionException, NegativeTradeException, InsufficientBalanceException;

}
