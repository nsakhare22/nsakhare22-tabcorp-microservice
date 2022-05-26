package com.codetest.tabcorp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codetest.tabcorp.models.ProductCost;


/**
 * Transaction Repository for Product Table
 *
 */
public interface ProductCostRepo extends JpaRepository<ProductCost, Integer>{
	
	  
	  @Query(value = "SELECT p.code,t.quantity,p.cost,sum(quantity)*sum(cast(cost as int)) as total_amount"
		  		+ " from transaction t, product p"
		  		+ " where t.product_code= p.code "
		  		+ " group by p.code, t.quantity", nativeQuery = true)
	  List<ProductCost> findByCode();
}
