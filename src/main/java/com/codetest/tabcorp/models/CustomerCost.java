package com.codetest.tabcorp.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * CustomerCost Entity
 * The table contains id, firstname, lastname, numTransaction and location.
 * 
 */
@Entity
public class CustomerCost {

	@Id
	private long id;
	
	private String firstname;
	
	private String lastname;
	
	@Column(name="num_transaction")
	private long numTransaction;
	
	private String location;

	public CustomerCost(long id, String firstname, String lastname, long numTransaction, String location) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.numTransaction = numTransaction;
		this.location = location;
	}
	
	public CustomerCost() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public long getNumTransaction() {
		return numTransaction;
	}

	public void setNumTransaction(long numTransaction) {
		this.numTransaction = numTransaction;
	}

	@Override
	public String toString() {
		return "CustomerCost [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", numTransaction="
				+ numTransaction + ", location=" + location + "]";
	}
}
