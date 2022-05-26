package com.codetest.tabcorp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * TransactionCost Entity mapped to the transactioncost table
 * The table contains id, code, quantity and total_amount.
 * 
 */
@Entity
public class ProductCost {
	
	@Id
	private String code;
	
	private long quantity;
	
	private long cost;
	
	@Column(name="total_amount")
	private long totalAmount;
	
	public ProductCost(String code, long quantity, long cost, long totalAmount) {
		super();
		this.code = code;
		this.quantity = quantity;
		this.cost = cost;
		this.totalAmount = totalAmount;
	}
	
	public ProductCost() {
		super();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public long getCost() {
		return cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "ProductCost [code=" + code + ", quantity=" + quantity + ", cost=" + cost + ", totalAmount="
				+ totalAmount + "]";
	}
}
