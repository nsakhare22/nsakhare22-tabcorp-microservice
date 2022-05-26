package com.codetest.tabcorp.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codetest.tabcorp.dao.CustomerCostRepo;
import com.codetest.tabcorp.dao.ProductCostRepo;
import com.codetest.tabcorp.dao.ProductRepo;
import com.codetest.tabcorp.dao.TransactionCostRepo;
import com.codetest.tabcorp.dao.TransactionRepo;
import com.codetest.tabcorp.enums.TransactionEnum;
import com.codetest.tabcorp.models.CustomerCost;
import com.codetest.tabcorp.models.Product;
import com.codetest.tabcorp.models.ProductCost;
import com.codetest.tabcorp.models.Transaction;
import com.codetest.tabcorp.models.TransactionCost;

/**
 * 
 * transaction service for inserting data,
 * getting various report and validating the data
 *
 */
@Service
public class TransactionServiceImpl implements TransactionService {

	Logger LOG = LoggerFactory.getLogger(TransactionService.class);
	
	@Autowired
	TransactionRepo transactionRepo;
	
	@Autowired
	TransactionCostRepo transactionCostRepo;
	
	@Autowired
	ProductCostRepo productCostRepo;
	
	@Autowired
	CustomerCostRepo customerCostRepo;
	
	@Autowired
	ProductRepo productRepo;
	

	@Override
	public String insertAll(List<Transaction> data) {
		if (data != null && !data.isEmpty()) {
			String status = processData(data);
			return status;
		} else {
			return TransactionEnum.EMPTY_DATA;
		}
	}
	
	@Override
	public List<TransactionCost> getCTReport() {
		return transactionCostRepo.findByID();

	}
	
	@Override
	public List<ProductCost> getPTReport() {
		return productCostRepo.findByCode();

	}
	
	@Override
	public List<CustomerCost> findByLocation() {
		return customerCostRepo.findByLocation();
	}

	/**
	 * @param transactionData - transaction data sent to the service from postman
	 *  
	 *  The method will validate data by calling ValidateTransactionData and if valid,
	 *  saving to database by calling saveTransactionData
	 * 
	 * @return transaction status
	 * **/
	private String processData(List<Transaction> transactionData) {
		LOG.info("Processing data..");
		String validationStatus = ValidateTransactionData(transactionData);
		if(!validationStatus.isEmpty())
			return validationStatus;
		else
			return saveTransactionData(transactionData);
	}

	/**
	 * 
	 * @param transactionData
	 * 
	 * The method will iterate over the data and calls validateTransaction
	 * and later ValidateCost
	 * returns a message to the postman about the transaction status 
	 * 
	 ***/
	private String ValidateTransactionData(List<Transaction> transactionData) {
		for (Transaction transaction : transactionData) {
			String validationMessage = validateTransaction(transaction);
			if (validationMessage.length() > 0)
				return validationMessage;
		}
		return ValidateCost(transactionData);
		
	}

	/**
	 * @param transaction 
	 *  validates the transaction date with current date
	 *  Along with this, the method will validate products and 
	 *  status of the product
	 *  
	 *  @return transaction status
	 * **/
	private String validateTransaction(Transaction transaction) {
		LOG.info("Validating transaction.."+transaction.getTime());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(TransactionEnum.DATE_PATTERN);
		LocalDateTime now = LocalDateTime.now();
		if (dtf.format(now).compareTo(transaction.getTime()) > 0)
			return TransactionEnum.PAST_DATE;
		else if (findStatus(transaction))
			return TransactionEnum.INACTIVE_STATUS;
		else
			return TransactionEnum.EMPTY;
	}

	/***
	 * @param transaction
	 * 
	 * Checks the status of the product and
	 * if inactive or the product is not valid, sets true flag
	 * 
	 * @returns true if product has inactive status
	 * false if the product is active
	 * 
	 * ***/
	private boolean findStatus(Transaction transaction) {
		LOG.info("find the product status..");
		Product product = productRepo.findByCode(transaction.getProduct().getCode());
		if (product != null) {
			String status = product.getStatus();
			LOG.debug("the product status.."+status);
			return status.equalsIgnoreCase(TransactionEnum.INACTIVE);
		} else {
			return true;
		}
	}

	/***
	 * 
	 * @param transactionData - transaction data
	 * 
	 * Checks if the total cost of transaction is more than 5000
	 * by calculating the cost of each transaction using calculateCost
	 * method and sets true flag is the cost exceeds.
	 * 
	 * If data is valid calls saveTransactionData
	 * 
	 * @returns status message of the validation and data transaction
	 * 
	 * 
	 * ***/
	private String ValidateCost(List<Transaction> transactionData) {
		long totalCost = transactionData
		.stream()
		.map(s -> calculateCost(s))
		.collect(Collectors.toList())
		.stream()
		.mapToLong(Long::longValue)
		.sum();
		if (totalCost > 5000)
			return TransactionEnum.COST_EXCEED;
		else
			return TransactionEnum.EMPTY;
			
	}

	/***
	 * @param transactionData - transaction data
	 * 
	 * Saves the data to database
	 * 
	 * @returns status message of data transaction
	 * 
	 * 
	 * ***/
	private String saveTransactionData(List<Transaction> transactionData) {
		String status = TransactionEnum.TRANSACTION_SUCCESS;
		try {
			List<Long> ids = transactionData.stream()
		            .map(transaction -> transaction.getCustomer().getId())
		            .collect(Collectors.toList());
		List<Transaction> existingTransactionData = transactionRepo.findAllTransactionsWithIDs(ids);
		transactionRepo.deleteAll(existingTransactionData);
		transactionRepo.saveAll(transactionData);
		} catch(Exception e) {
			LOG.error("Error while inserting data",e);
			status = TransactionEnum.TRANSACTION_FAILURE;
		}
		return status;
	}

	/***
	 * @param transaction
	 * 
	 * calculates the cost of each transaction and sends 
	 * it back to ValidateCost which will use this 
	 * for adding the total cost of the transaction
	 * 
	 * @returns cost of each transaction
	 * 
	 * 
	 * ***/
	private long calculateCost(Transaction transaction) {
		long cost = productRepo.findByCode(transaction.getProduct().getCode()).getCost();
		return transaction.getQuantity() * cost;
	}
}
