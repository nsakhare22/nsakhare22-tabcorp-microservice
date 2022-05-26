package com.codetest.tabcorp.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


/**
 * 
 * Transaction Entity mapped to the transaction table
 * The table contains tabID, customerID, product_code, time and quantity.
 * 
 */
@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long tabID;

	@ManyToOne
	@JoinColumn(name = "customerID", referencedColumnName = "id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "product_code", referencedColumnName = "code")
	private Product product;

	private String time;
	
	private long quantity;

	public Transaction(long tabID, Customer customer, Product product, String time, long quantity) {
		super();
		this.tabID = tabID;
		this.customer = customer;
		this.product = product;
		this.time = time;
		this.quantity = quantity;
	}
	
	public Transaction() {
		super();
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "Transaction [customer_id=" + customer.getId() + ", product_code=" + product.getCode() + ", time=" + time
				+ ", quantity=" + quantity + "]";
	}

}
