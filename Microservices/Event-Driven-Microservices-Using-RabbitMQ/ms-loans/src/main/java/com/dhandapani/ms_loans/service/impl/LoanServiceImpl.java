package com.dhandapani.ms_loans.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.dhandapani.ms_loans.constant.LoanConstants;
import com.dhandapani.ms_loans.dto.LoanDto;
import com.dhandapani.ms_loans.entity.Loan;
import com.dhandapani.ms_loans.exception.ResourceAlreadyExistsException;
import com.dhandapani.ms_loans.exception.ResourceNotFoundException;
import com.dhandapani.ms_loans.mapper.LoanMapper;
import com.dhandapani.ms_loans.repository.LoanRepository;
import com.dhandapani.ms_loans.service.ILoanService;

import lombok.AllArgsConstructor;

/**
 * @author Dhandapani Sudhakar
 * 
 *         AllArgsConstructor It will create only one args constructor and the
 *         argument will be type of LoanRepository, in this case we don't need
 *         autowire it using Autowired annotation
 */
@Service
@AllArgsConstructor
public class LoanServiceImpl implements ILoanService {

	private LoanRepository loanRepository;

	private Loan createNewLoan(String mobileNumber) {
		Loan newLoan = new Loan();
		long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
		newLoan.setLoanNumber(Long.toString(randomLoanNumber));
		newLoan.setMobileNumber(mobileNumber);
		newLoan.setLoanType(LoanConstants.HOME_LOAN);
		newLoan.setTotalLoanAmount(LoanConstants.NEW_LOAN_LIMIT);
		newLoan.setOutstandingAmount(LoanConstants.NEW_LOAN_LIMIT);
		newLoan.setAmountPaid(0);
		return newLoan;
	}

	@Override
	public void createLoanByMobileNumber(String mobileNumber) {
		Optional<Loan> optionalLoan = loanRepository.findByMobileNumber(mobileNumber);
		if (optionalLoan.isPresent()) {
			throw new ResourceAlreadyExistsException("Loan", "mobileNumber", mobileNumber);
		}
		loanRepository.save(createNewLoan(mobileNumber));
	}

	@Override
	public LoanDto fetchLoanByMobileNumber(String mobileNumber) {
		Loan foundLoan = loanRepository.findByMobileNumber(mobileNumber).orElseThrow( () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
		return LoanMapper.mapToLoanDto(foundLoan, new LoanDto());
	}

	@Override
	public boolean updateLoan(LoanDto loanDto) {
		Loan foundLoan = loanRepository.findByLoanNumber(loanDto.getLoanNumber()).orElseThrow(() -> new ResourceNotFoundException("Loan", "loanNumber", loanDto.getLoanNumber()));
		Loan loanToUpdate = LoanMapper.mapToLoan(loanDto, foundLoan);
		loanRepository.save(loanToUpdate);
		return true;
	}

	@Override
	public boolean deleteLoanByMobileNumber(String mobileNumber) {
		Loan foundLoan = loanRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
		loanRepository.deleteById(foundLoan.getLoanId());
		return true;
	}

}
