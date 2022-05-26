package com.codetest.tabcorp.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.codetest.tabcorp.dao.ProductRepo;
import com.codetest.tabcorp.dao.TransactionCostRepo;
import com.codetest.tabcorp.dao.TransactionRepo;
import com.codetest.tabcorp.models.Customer;
import com.codetest.tabcorp.models.CustomerCost;
import com.codetest.tabcorp.models.Product;
import com.codetest.tabcorp.models.ProductCost;
import com.codetest.tabcorp.models.Transaction;
import com.codetest.tabcorp.models.TransactionCost;
import com.codetest.tabcorp.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Unit test for TransactionController.
 * 
 * 
 * **/
@ExtendWith(SpringExtension.class)
@WebMvcTest
public class TransactionControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	TransactionService transactionService;

	@InjectMocks
	TransactionController transactionController;

	@Autowired
	ObjectMapper objectMapper;

	@Mock
	private ProductRepo productRepo;

	@Mock
	private TransactionRepo transactionRepo;

	@Mock
	private TransactionCostRepo transactionCostRepo;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
	}
	
	/**
	 * Tests customerTransactionReport method by 
	 * using dummy data
	 * 
	 * **/
	@Test
	public void addTransactionTest() throws Exception {
		Customer customerOne = new Customer(1, "tony", "tony", "tony@abc.com","Australia");
		Customer customerTwo = new Customer(2, "stark", "stark", "stark@abc.com","US");
		Customer customerThree = new Customer(3, "Emy", "Emy", "Emy@abc.com","Canada");
		
		Product productOne = new Product("Product_001", 40, "Active");
		Product productTwo = new Product("Product_002", 10, "Active");
		Product productThree = new Product("Product_003", 20, "Active");
		
		Transaction transactionOne = new Transaction(1, customerOne,productOne, "2022-07-08 14:56", 2);
		Transaction transactionTwo = new Transaction(2, customerTwo,productTwo, "2022-07-08 14:56", 5);
		Transaction transactionThree = new Transaction(3, customerThree,productThree, "2022-07-08 14:56", 8);

		List<Transaction> transactions = Arrays.asList(transactionOne, transactionTwo,transactionThree);
		
		when(transactionService.insertAll(transactions)).thenReturn(anyString());

		mockMvc.perform(post("/addTransaction")
			.content(objectMapper.writeValueAsString(transactions))
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		assertEquals(anyString(), transactionService.insertAll(transactions));
	}

	/**
	 * Tests customerTransactionReport method by 
	 * using dummy data
	 * 
	 * **/
	@Test
	public void customerTransactionReportTest() throws Exception {
		TransactionCost transactionCostOne = new TransactionCost(10001, "PRODUCT_001", 4, 10, 200);
		TransactionCost transactionCostTwo = new TransactionCost(10002, "PRODUCT_002", 10, 20, 100);
		TransactionCost transactionCostThree = new TransactionCost(10003, "PRODUCT_003", 20, 30, 300);

		List<TransactionCost> transactionCostData = Arrays.asList(transactionCostOne, transactionCostTwo,
				transactionCostThree);

		Mockito.when(transactionService.getCTReport()).thenReturn(transactionCostData);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/customerTransaction")
				.contentType(MediaType.APPLICATION_JSON_VALUE);

		mockMvc.perform(mockRequest)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[1].code", is("PRODUCT_002")))
			.andExpect(jsonPath("$.length()", is(3)));
		
		assertEquals(transactionCostData, transactionService.getCTReport());
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

		Mockito.when(transactionService.getPTReport()).thenReturn(productCosts);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/productTransaction")
				.contentType(MediaType.APPLICATION_JSON_VALUE);

		mockMvc.perform(mockRequest)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[3].code", is("PRODUCT_007")))
			.andExpect(jsonPath("$.length()", is(4)));
		
		assertEquals(productCosts, transactionService.getPTReport());
	}

	/**
	 * Tests findByLocation method by 
	 * using dummy data
	 * 
	 * **/
	@Test
	public void findByLocationTest() throws Exception {
		CustomerCost customerCostOne = new CustomerCost(100001, "Tony", "Stark", 20, "US");
		CustomerCost customerCostTwo = new CustomerCost(100001, "Amy", "Peter", 30, "Canada");

		List<CustomerCost> customerCosts = Arrays.asList(customerCostOne, customerCostTwo);

		Mockito.when(transactionService.findByLocation()).thenReturn(customerCosts);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/findByLocation")
				.contentType(MediaType.APPLICATION_JSON_VALUE);

		mockMvc.perform(mockRequest)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].firstname", is("Tony")))
			.andExpect(jsonPath("$.length()", is(2)));
		
		assertEquals(customerCosts, transactionService.findByLocation());
	}

}
