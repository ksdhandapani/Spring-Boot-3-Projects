package com.dhandapani.ms_loans.service;

import com.dhandapani.ms_loans.dto.LoanDto;

/**
 * @author Dhandapani Sudhakar
 */
public interface ILoanService {

	void createLoanByMobileNumber(String mobileNumber);
	
	LoanDto fetchLoanByMobileNumber(String mobileNumber);
	
	boolean updateLoan(LoanDto loanDto);
	
	boolean deleteLoanByMobileNumber(String mobileNumber);
}
