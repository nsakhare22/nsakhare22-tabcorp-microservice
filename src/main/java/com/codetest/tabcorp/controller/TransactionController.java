/**
 * 
 */
package com.codetest.tabcorp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.codetest.tabcorp.models.Transaction;
import com.codetest.tabcorp.service.TransactionService;

/**
 * @author naveen.sakhare
 *
 */
@RestController
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	@ResponseBody
	@RequestMapping(value = "/addTransaction",
	        consumes = { MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.APPLICATION_JSON_VALUE },
	        method = RequestMethod.POST)
	public String addTransaction(@RequestBody List<Transaction> data) {
		return transactionService.insertAll(data);
	}
	
	@RequestMapping(value="/customerTransaction")
	public void customerTransactionReport() {
		transactionService.getCTReport();
	}
}
