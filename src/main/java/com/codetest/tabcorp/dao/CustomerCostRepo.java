package com.codetest.tabcorp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codetest.tabcorp.models.CustomerCost;


/**
 * Transaction Repository for Customer Table
 *
 */
public interface CustomerCostRepo extends JpaRepository<CustomerCost, Integer>{
	
	@Query(value = "SELECT c.id, c.firstname, c.lastname, c.location, count(c.id)*sum(t.quantity) as num_transaction"
	  		+ " from customer c, transaction t"
	  		+ " where c.location = 'Australia'"
	  		+ " and c.id=t.customerid"
	  		+ " group by c.id", nativeQuery = true)
	  List<CustomerCost> findByLocation();

}
