package com.codetest.tabcorp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codetest.tabcorp.models.Product;
import com.codetest.tabcorp.models.Transaction;


/**
 * Transaction Repository for Transaction Table
 *
 */
public interface TransactionRepo extends JpaRepository<Transaction, Integer>{
	/*
	 * @Query(value = "select c.id," +
	 * " (Select count(t.quantity) from Transaction t where t.customerid=c.id) as quantities"
	 * + " from Transaction t1 order by t1.customerid", nativeQuery = true) Product
	 * findByCustomerID();
	 */
	
	@Query(value = "Select product_code from Transaction where customerID= ?1", nativeQuery = true)
	String findByCustomerID(long id);
}
