package com.dhandapani.ms_loans.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Dhandapani Sudhakar
 */
@Entity
@Table(name="loans")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Loan extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="loan_id")
	private Long loanId;
	
	@Column(name="mobile_number")
	private String mobileNumber;
	
	@Column(name="loan_number")
	private String loanNumber;
	
	@Column(name="loan_type")
	private String loanType;
	
	@Column(name="total_loan_amount")
	private int totalLoanAmount;
	
	@Column(name="amount_paid")
	private int amountPaid;
	
	@Column(name="outstanding_amount")
	private int outstandingAmount;

}
