package com.dhandapani.ms_loans.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhandapani.ms_loans.entity.Loan;

/**
 * @author Dhandapani Sudhakar
 */
@Repository
public interface LoanRepository extends JpaRepository<Loan, Long>{

	Optional<Loan> findByMobileNumber(String mobileNumber);
	
	Optional<Loan> findByLoanNumber(String loanNumber);
}
