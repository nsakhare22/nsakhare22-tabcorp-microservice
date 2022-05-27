package com.codetest.tabcorp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.codetest.tabcorp.models.CustomerCost;
import com.codetest.tabcorp.models.ProductCost;
import com.codetest.tabcorp.models.Transaction;
import com.codetest.tabcorp.models.TransactionCost;
import com.codetest.tabcorp.service.TransactionService;

/**
 * Controller to handle endpoints for a number of services
 *
 */
@RestController
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	/***
	 * 
	 * @param data - Sent from Postman
	 * 
	 * The method will insert all data by calling
	 * insertAll method of addTransaction
	 * and returns appropriate transaction status message.
	 * 
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addTransaction",
	        consumes = { MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.APPLICATION_JSON_VALUE },
	        method = RequestMethod.POST)
	public String addTransaction(@RequestBody List<Transaction> data) {
		return transactionService.insertAll(data);
	}
	
	/***
	 * 
	 * The method will return the
	 * total cost of transactions per customer
	 * 
	 ***/
	@RequestMapping(value="/customerTransaction")
	public List<TransactionCost> customerTransactionReport() {
		return transactionService.getCTReport();
	}
	
	/***
	 * 
	 * The method will return the
	 * Total cost of transactions per product
	 * 
	 ***/
	@RequestMapping(value="/productTransaction")
	public List<ProductCost> productTransactionReport() {
		return transactionService.getPTReport();
	}
	
	/***
	 * 
	 * The method will return the
	 * Number of transactions sold to customer from Australia
	 * 
	 ***/
	@RequestMapping(value="/findByLocation")
	public List<CustomerCost> findByLocation() {
		return transactionService.findByLocation();
	}
	
}
