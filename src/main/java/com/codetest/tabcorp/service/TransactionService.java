package com.codetest.tabcorp.service;

import java.util.List;

import com.codetest.tabcorp.models.CustomerCost;
import com.codetest.tabcorp.models.ProductCost;
import com.codetest.tabcorp.models.Transaction;
import com.codetest.tabcorp.models.TransactionCost;

/**
 * Service Interface to expose necessary methods
 * 
 * **/
public interface TransactionService {
	
	String insertAll(List<Transaction> tabCorp);

	List<TransactionCost> getCTReport();

	List<ProductCost> getPTReport();

	List<CustomerCost> findByLocation();

}
