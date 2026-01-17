package com.dhandapani.ms_loans.mapper;

import com.dhandapani.ms_loans.dto.LoanDto;
import com.dhandapani.ms_loans.entity.Loan;

/**
 * @author Dhandapani Sudhakar
 */
public class LoanMapper {

	public static Loan mapToLoan(LoanDto loanDto, Loan loan) {
		loan.setLoanNumber(loanDto.getLoanNumber());
		loan.setLoanType(loanDto.getLoanType());
		loan.setMobileNumber(loanDto.getMobileNumber());
		loan.setTotalLoanAmount(loanDto.getTotalLoanAmount());
		loan.setOutstandingAmount(loanDto.getOutstandingAmount());
		loan.setAmountPaid(loanDto.getAmountPaid());
		return loan;
	}

	public static LoanDto mapToLoanDto(Loan loan, LoanDto loanDto) {
		loanDto.setLoanNumber(loan.getLoanNumber());
		loanDto.setLoanType(loan.getLoanType());
		loanDto.setMobileNumber(loan.getMobileNumber());
		loanDto.setTotalLoanAmount(loan.getTotalLoanAmount());
		loanDto.setOutstandingAmount(loan.getOutstandingAmount());
		loanDto.setAmountPaid(loan.getAmountPaid());
		return loanDto;
	}
}
