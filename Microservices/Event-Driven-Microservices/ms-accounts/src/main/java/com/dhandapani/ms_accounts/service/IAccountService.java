package com.dhandapani.ms_accounts.service;

import com.dhandapani.ms_accounts.dto.CustomerDto;

public interface IAccountService {

	/**
	 * 
	 * @param customerDto
	 */
	void createAccount(CustomerDto customerDto);
	
	/**
	 * 
	 * @param mobileNumber
	 * @return
	 */
	CustomerDto fetchAccountByMobileNumber(String mobileNumber);
	
	/**
	 * 
	 * @param customerDto
	 * @return
	 */
	boolean updateAccount(CustomerDto customerDto);
	
	boolean deleteAccount(String mobileNumber);
	
	boolean updateCommunicationStatus(Long accountNumber);

}
