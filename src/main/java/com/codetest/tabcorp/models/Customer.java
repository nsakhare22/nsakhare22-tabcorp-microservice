package com.codetest.tabcorp.models;


import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * Customer Entity mapped to the customer table
 * The table contains id, firstname, lastname, email and location.
 * 
 */
@Entity
public class Customer {

	@Id
	private long id;
	
	private String firstname;
	
	private String lastname;
	
	private String email;
	
	private String location;

	public Customer(long id, String firstname, String lastname, String email, String location) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.location = location;
	}
	
	public Customer() {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
				+ ", location=" + location + "]";
	}
	
}
