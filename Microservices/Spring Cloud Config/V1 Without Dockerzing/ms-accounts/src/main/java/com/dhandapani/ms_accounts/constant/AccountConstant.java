package com.dhandapani.ms_accounts.constant;

public class AccountConstant {
	
	
	/**
	 * We do not want anyone to create object for this class because inside this class we want to keep only constants
	 */
	private AccountConstant() {
		// restrict instantiation
	}

	public static final String SAVINGS = "Savings";
	
	public static final String ADDRESS = "123 Main Street, New York";
	
	public static final String STATUS_201 = "201";
	public static final String MESSAGE_201 = "Account created successfully";
	
	public static final String STATUS_200 = "200";
	public static final String MESSAGE_200 = "Request processed successfully";
	
	public static final String STATUS_417 = "417";
	public static final String MESSAGE_417_UPDATE = "Update operation failed. Please try again or contact Dev Team";
	public static final String MESSAGE_417_DELETE = "Delete operation failed. Please try again or contact Dev Team";

	public static final String STATUS_500 = "500";
	public static final String MESSAGE_500 = "An error occurred. Please try again or contact Dev Team";

}
