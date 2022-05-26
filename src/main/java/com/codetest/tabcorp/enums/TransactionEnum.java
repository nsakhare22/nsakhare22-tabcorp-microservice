
package com.codetest.tabcorp.enums;

/**
 * final class for all constants
 *
 */
public final class TransactionEnum {
	
	public static final String ACTIVE = "Active";
	
	public static final String INACTIVE = "Inactive";
	
	public static final String COST_EXCEED = "Transaction failed - Total cost of transaction must not exceed 5000";
	
	public static final String TRANSACTION_SUCCESS = "Transaction successful";
	
	public static final String TRANSACTION_FAILURE = "Transaction failed - Error while inserting data";
	
	public static final String PAST_DATE = "Transaction failed - Contains past date";
	
	public static final String INACTIVE_STATUS = "Transaction failed - One of the product is inactive or the product doesn't exist";
	
	public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";
	
	public static final String EMPTY_DATA = "Transaction failed - data is empty.";
	
	public static final String EMPTY = "";
	
	public static final String DUPLICATE_PRODUCTS = "Transaction failed - contains duplicate products for the customer";

}
