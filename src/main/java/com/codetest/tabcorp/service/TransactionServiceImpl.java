package com.codetest.tabcorp.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codetest.tabcorp.dao.ProductRepo;
import com.codetest.tabcorp.dao.TransactionRepo;
import com.codetest.tabcorp.enums.TransactionEnum;
import com.codetest.tabcorp.models.Product;
import com.codetest.tabcorp.models.Transaction;

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
	public void getCTReport() {
		//System.out.println("testing" + transactionRepo.findByCustomerID());
		
		
	}

	/**
	 * @param transactionData - transaction data sent to the service from postman
	 *  
	 *  The method will iterate over the data and calls validateTransaction, ValidateCostandSave
	 *  for each transaction and returns a message to the postman about the transaction status 
	 * 
	 * @return transaction status
	 * **/
	private String processData(List<Transaction> transactionData) {
		LOG.info("Processing data..");
		for (Transaction transaction : transactionData) {
			String validationMessage = validateTransaction(transaction);
			if (!validationMessage.isEmpty())
				return validationMessage;
		}
		return ValidateCostAndSaveData(transactionData);
	}

	/**
	 * @param transaction 
	 *  validates the transaction date with current date and sends an
	 *  invalidate message if the date is past
	 *  Along with this, the method will validate products and 
	 *  status of the product
	 *  
	 *  @return transaction status
	 * **/
	private String validateTransaction(Transaction transaction) {
		LOG.info("Validating transaction..");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(TransactionEnum.DATE_PATTERN);
		LocalDateTime now = LocalDateTime.now();
		if (dtf.format(now).compareTo(transaction.getTime()) > 0)
			return TransactionEnum.PAST_DATE;
		else if (duplicateProduct(transaction))
			return TransactionEnum.DUPLICATE_PRODUCTS;
		else if (findStatus(transaction))
			return TransactionEnum.INACTIVE_STATUS;
		else
			return "";
	}


	/***
	 * @param transaction
	 * 
	 * Checks if the product in transaction table exist in Product table
	 * and not present, sets true flag
	 * 
	 * @returns true if product is already present
	 * false if the product is new
	 * 
	 * ***/
	private boolean duplicateProduct(Transaction transaction) {
		LOG.debug("contains duplicate product?");
		String productCode = transactionRepo.findByCustomerID(transaction.getCustomer().getId());
		if (productCode != null ) {
			if (productCode.isEmpty())
				return true;
			else if (productCode.equalsIgnoreCase(transaction.getProduct().getCode()))
				return true;
			else
				return false;
		}
		return false;
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
	private String ValidateCostAndSaveData(List<Transaction> transactionData) {
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
			return saveTransactionData(transactionData);
			
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
		transactionRepo.saveAll(transactionData);
		return TransactionEnum.TRANSACTION_SUCCESS;
	}

	/***
	 * @param transaction
	 * 
	 * calculates the cost of each transaction and sends 
	 * it back to ValidateCostAndSaveData which will use this 
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