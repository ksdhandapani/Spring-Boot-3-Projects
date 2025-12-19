package com.dhandapani.ms_accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(name="Account", description="Schema to hold Account information")
@Data
public class AccountDto {

	@Schema(description="Customer Account Number")
	@NotEmpty(message = "Account number cannot be null or empty")
	@Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
	private Long accountNumber;

	@Schema(description="Type of Account", example = "Savings")
	@NotEmpty(message = "Account type cannot be null or empty")
	private String accountType;

	@Schema(description="Bank's branch address")
	@NotEmpty(message = "Branch address cannot be null or empty")
	private String branchAddress;
}
