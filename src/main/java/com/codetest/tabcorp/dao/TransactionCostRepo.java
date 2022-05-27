package com.codetest.tabcorp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codetest.tabcorp.models.TransactionCost;


/**
 * Transaction Repository for Product Table
 *
 */
@Repository
public interface TransactionCostRepo extends JpaRepository<TransactionCost, Integer>{
	
	  @Query(value = "SELECT c.id, p.code, p.cost, sum(quantity) as Quantity, sum(quantity)*sum(cast(cost as int)) as total_amount"
	  		+ " from customer c, transaction t, product p"
	  		+ " where c.id = t.customerid and"
	  		+ " p.code = t.product_code"
	  		+ " group by c.id, p.code", nativeQuery = true)
	  public List<TransactionCost> findByID();
}
