package com.codetest.tabcorp.service;

import java.util.List;

import com.codetest.tabcorp.models.Transaction;

/**
 * Service Interface to expose necessary methods
 * 
 * **/
public interface TransactionService {
	
	String insertAll(List<Transaction> tabCorp);

	void getCTReport();

}
