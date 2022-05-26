package com.codetest.tabcorp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codetest.tabcorp.models.Transaction;


/**
 * Transaction Repository for Transaction Table
 *
 */
public interface TransactionRepo extends JpaRepository<Transaction, Integer>{
	@Query(value = "Select product_code from Transaction where customerID= ?1", nativeQuery = true)
	String findByCustomerID(long id);
}
