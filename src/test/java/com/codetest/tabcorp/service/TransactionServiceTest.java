package com.codetest.tabcorp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.codetest.tabcorp.dao.CustomerCostRepo;
import com.codetest.tabcorp.dao.ProductCostRepo;
import com.codetest.tabcorp.dao.ProductRepo;
import com.codetest.tabcorp.dao.TransactionCostRepo;
import com.codetest.tabcorp.dao.TransactionRepo;
import com.codetest.tabcorp.enums.TransactionEnum;
import com.codetest.tabcorp.models.Customer;
import com.codetest.tabcorp.models.CustomerCost;
import com.codetest.tabcorp.models.Product;
import com.codetest.tabcorp.models.ProductCost;
import com.codetest.tabcorp.models.Transaction;
import com.codetest.tabcorp.models.TransactionCost;

/**
 * Unit test for TransactionServiceTest.
 * 
 * 
 * **/
@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

	@InjectMocks
	TransactionServiceImpl transactionService;

	@Mock
	private ProductCostRepo productCostRepo;
	
	@Mock
	private ProductRepo productRepo;

	@Mock
	private CustomerCostRepo customerCostRepo;
	
	@Mock
    private TransactionRepo transactionRepo;
	 

	@Mock
	private TransactionCostRepo transactionCostRepo;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	

	/**
	 * Tests customerTransactionReport method by 
	 * using dummy data
	 * 
	 * **/
	@Test
	public void addTransactionInactiveProductTest() throws Exception {
		Customer customerOne = new Customer(1, "tony", "tony", "tony@abc.com","Australia");
		Customer customerTwo = new Customer(2, "stark", "stark", "stark@abc.com","US");
		Customer customerThree = new Customer(3, "Emy", "Emy", "Emy@abc.com","Canada");
		
		Product productOne = new Product("Product_001", 40, "Active");
		Product productTwo = new Product("Product_002", 10, "InActive");
		Product productThree = new Product("Product_003", 20, "Active");
		
		Transaction transactionOne = new Transaction(1, customerOne,productOne, "2022-07-08 14:56", 2);
		Transaction transactionTwo = new Transaction(2, customerTwo,productTwo, "2022-07-08 14:56", 5);
		Transaction transactionThree = new Transaction(3, customerThree,productThree, "2022-07-08 14:56", 8);

		List<Transaction> transactions = Arrays.asList(transactionOne, transactionTwo,transactionThree);
		
		assertThat(transactionService.insertAll(transactions)).isEqualTo(TransactionEnum.INACTIVE_STATUS);
	}
	
	/**
	 * Tests customerTransactionReport method by 
	 * using dummy data
	 * 
	 * **/
	@Test
	public void addTransactionPastDateTest() throws Exception {
		Customer customerOne = new Customer(10001, "tony", "stark", "tony.stark@gmail.com","Australia");
		Customer customerTwo = new Customer(10002, "Bruce", "Banner", "bruce.banner@gmail.com","US");
		
		Product productOne = new Product("Product_001", 50, "Active");
		Product productTwo = new Product("Product_003", 200, "Active");
		
		Transaction transactionOne = new Transaction(1, customerOne,productOne, "2021-07-08 14:56", 2);
		Transaction transactionTwo = new Transaction(2, customerTwo,productTwo, "2022-07-08 14:56", 5);

		List<Transaction> transactions = Arrays.asList(transactionOne, transactionTwo);
		
		assertThat(transactionService.insertAll(transactions)).isEqualTo(TransactionEnum.PAST_DATE);
	}
	
	/**
	 * Tests customerTransactionReport method by 
	 * using dummy data
	 * 
	 * **/
	@Test
	public void addTransactionProductCostTest() throws Exception {
		Customer customerOne = new Customer(10001, "tony", "stark", "tony.stark@gmail.com","Australia");
		Customer customerTwo = new Customer(10002, "Bruce", "Banner", "bruce.banner@gmail.com","US");
		
		Product productOne = new Product("Product_001", 50, "Active");
		Product productTwo = new Product("Product_005", 5001, "Active");
		
		Transaction transactionOne = new Transaction(1, customerOne,productOne, "2022-07-08 14:56", 2);
		Transaction transactionTwo = new Transaction(2, customerTwo,productTwo, "2022-07-08 14:56", 5);
		List<Transaction> transactions = Arrays.asList(transactionOne, transactionTwo);
		
		when(productRepo.findByCode("Product_001")).thenReturn(productOne);
		when(productRepo.findByCode("Product_005")).thenReturn(productTwo);
		assertThat(transactionService.insertAll(transactions)).isEqualTo(TransactionEnum.COST_EXCEED);
	}
	
	/**
	 * Tests customerTransactionReport method by 
	 * using dummy data
	 * 
	 * **/
	@Test
	public void addTransactionValidTest() throws Exception {
		Customer customerOne = new Customer(10001, "tony", "stark", "tony.stark@gmail.com","Australia");
		Customer customerTwo = new Customer(10002, "Bruce", "Banner", "bruce.banner@gmail.com","US");
		
		Product productOne = new Product("Product_001", 50, "Active");
		Product productTwo = new Product("Product_005", 200, "Active");
		
		Transaction transactionOne = new Transaction(1, customerOne,productOne, "2022-07-08 14:56", 2);
		Transaction transactionTwo = new Transaction(2, customerTwo,productTwo, "2022-07-08 14:56", 5);
		List<Transaction> transactions = Arrays.asList(transactionOne, transactionTwo);
		
		when(productRepo.findByCode("Product_001")).thenReturn(productOne);
		when(productRepo.findByCode("Product_005")).thenReturn(productTwo);
		assertThat(transactionService.insertAll(transactions)).isEqualTo(TransactionEnum.TRANSACTION_SUCCESS);
	}

	/**
	 * Tests customerTransactionReport method by 
	 * using dummy data
	 * 
	 * **/
	@Test
	public void customerTransactionReportValidTest() throws Exception {
		TransactionCost transactionCostOne = new TransactionCost(10001, "PRODUCT_001", 4, 10, 200);
		TransactionCost transactionCostTwo = new TransactionCost(10002, "PRODUCT_002", 10, 20, 100);
		TransactionCost transactionCostThree = new TransactionCost(10003, "PRODUCT_003", 20, 30, 300);

		List<TransactionCost> transactionCostData = Arrays.asList(transactionCostOne, transactionCostTwo,
				transactionCostThree);
		
		when(transactionCostRepo.findByID()).thenReturn(transactionCostData);
		
		assertThat(transactionService.getCTReport().size()).isEqualTo(3);
		assertFalse(transactionService.getCTReport().isEmpty());
	}
	
	/**
	 * Tests customerTransactionReport method by 
	 * using dummy data
	 * 
	 * **/
	@Test
	public void customerTransactionReportEmptyTest() throws Exception {
		
		when(transactionCostRepo.findByID()).thenReturn(null);
		
		assertNull(transactionService.getCTReport());
	}
	
	/**
	 * Tests productTransaction method by 
	 * using dummy data
	 * 
	 * **/
	@Test
	public void productTransactionReportTest() throws Exception {
		ProductCost productCostOne = new ProductCost("PRODUCT_001", 2, 50, 200);
		ProductCost productCostTwo = new ProductCost("PRODUCT_004", 20, 100, 10);
		ProductCost productCostThree = new ProductCost("PRODUCT_006", 30, 5, 40);
		ProductCost productCostFour = new ProductCost("PRODUCT_007", 70, 5, 50);

		List<ProductCost> productCosts = Arrays.asList(productCostOne, productCostTwo, productCostThree,
				productCostFour);

		when(productCostRepo.findByCode()).thenReturn(productCosts);
		assertThat(transactionService.getPTReport().size()).isEqualTo(4);
	}
	
	/**
	 * Tests productTransaction method by 
	 * using dummy data
	 * 
	 * **/
	@Test
	public void productTransactionReportNullTest() throws Exception {

		when(productCostRepo.findByCode()).thenReturn(null);
		assertNull(transactionService.getPTReport());
	}

	/**
	 * Tests findByLocation method by 
	 * using dummy data
	 * 
	 * **/
	@Test
	public void findByLocationTest() throws Exception {
		CustomerCost customerCostOne = new CustomerCost(100001, "Tony", "Stark", 20, "US");
		CustomerCost customerCostTwo = new CustomerCost(100002, "Amy", "Peter", 30, "Canada");

		List<CustomerCost> customerCosts = Arrays.asList(customerCostOne, customerCostTwo);

		Mockito.when(customerCostRepo.findByLocation()).thenReturn(customerCosts);
		assertThat(transactionService.findByLocation().size()).isEqualTo(2);
	}
	
	/**
	 * Tests findByLocation method by 
	 * using dummy data
	 * 
	 * **/
	@Test
	public void findByLocationNullTest() throws Exception {

		Mockito.when(customerCostRepo.findByLocation()).thenReturn(null);
		assertNull(transactionService.findByLocation());
	}

}
