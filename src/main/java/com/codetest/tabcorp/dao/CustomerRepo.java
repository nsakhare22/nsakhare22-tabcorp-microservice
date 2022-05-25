package com.codetest.tabcorp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codetest.tabcorp.models.Customer;
import com.codetest.tabcorp.models.Product;


/**
 * Transaction Repository for Customer Table
 *
 */
public interface CustomerRepo extends JpaRepository<Customer, Integer>{
	
	 @Query(value = "from Product where code= ?1")
	 Product findByCode(String code);
	 

}
