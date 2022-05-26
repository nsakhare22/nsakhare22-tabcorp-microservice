package com.codetest.tabcorp.models;

import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * 
 * Product Entity mapped to the product table
 * The table contains code, cost and status.
 * 
 */
@Entity
public class Product {
	
	@Id
	private String code;
	
	private long cost;
	
	private String status;

	public Product(String code, long cost, String status) {
		super();
		this.code = code;
		this.cost = cost;
		this.status = status;
	}

	public Product() {
		super();
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getCost() {
		return cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Product [code=" + code + ", cost=" + cost + ", status=" + status + "]";
	}
	
	

}
