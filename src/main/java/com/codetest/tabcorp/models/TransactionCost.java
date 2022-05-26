package com.codetest.tabcorp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * TransactionCost Entity mapped to the transactioncost table
 * The table contains id, code, quantity and total_amount.
 * 
 */
@Entity
@Table(name="transaction_cost")
public class TransactionCost {
	
	private long id;
	
	@Id
	private String code;
	
	private long quantity;

	private long cost;
	
	@Column(name="total_amount")
	private long totalAmount;

	public TransactionCost(long id, String code, long quantity, long cost, long totalAmount) {
		super();
		this.id = id;
		this.code = code;
		this.quantity = quantity;
		this.cost = cost;
		this.totalAmount = totalAmount;
	}
	
	public TransactionCost() {
		super();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getQuantity() {
		return quantity;
	}

	public long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public long getCost() {
		return cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "TransactionCost [id=" + id + ", code=" + code + ", quantity=" + quantity + ", cost=" + cost
				+ ", totalAmount=" + totalAmount + "]";
	}
}
