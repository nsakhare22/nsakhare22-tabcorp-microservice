package com.codetest.tabcorp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codetest.tabcorp.models.Product;


/**
 * Transaction Repository for Product Table
 *
 */
public interface ProductRepo extends JpaRepository<Product, Integer>{
	
	 @Query(value = "from Product where code= ?1")
	 public Product findByCode(String code);
}
