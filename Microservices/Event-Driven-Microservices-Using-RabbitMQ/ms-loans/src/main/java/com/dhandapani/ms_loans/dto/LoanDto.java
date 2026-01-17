package com.dhandapani.ms_loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Dhandapani Sudhakar
 */
@Schema(name="Loan", description = "Schema to hold Loan information")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoanDto {

	@Schema(description = "Customer Mobile Number")
	@NotEmpty(message="Mobile Number cannot be empty or null")
	@Pattern(regexp="(^$|[0-9]{10})", message = "Mobile Number must be 10 digits")
	private String mobileNumber;
	
	@Schema(description = "Loan Number")
	@NotEmpty(message="Loan Number cannot be empty or null")
	@Pattern(regexp="(^$|[0-9]{12})", message = "Loan Number must be 12 digits")
	private String loanNumber;
	
	@Schema(description = "Type of Loan")
	@NotEmpty(message=" Loan Type cannot be empty or null")
	private String loanType;
	
	@Schema(description = "Total Loan Amount")
	@Positive(message = "Total Loan Amount should be greater tha zero")
	private int totalLoanAmount;
	
	@Schema(description = "Paid Loan Amount")
	@PositiveOrZero(message = "Total Amount Paid should be equal or greater than zero")
	private int amountPaid;
	
	@Schema(description = "Outstanding Loan Amount")
	@PositiveOrZero(message = "Total Outstanding Amount should be equal or greater than zero")
	private int outstandingAmount;
}
