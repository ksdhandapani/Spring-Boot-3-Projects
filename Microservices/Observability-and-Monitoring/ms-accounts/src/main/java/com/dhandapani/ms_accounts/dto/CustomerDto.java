package com.dhandapani.ms_accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "Customer", description = "Schema to hold Customer and Account information")
public class CustomerDto {

	@Schema(description="Name of the Customer", example="John Doe")
	@NotEmpty(message = "Name cannot be null or empty") // Making it mandatory
	@Size(min = 5, max = 30, message = "The length of the cusotmer name should be between 5 and 30")
	private String name;

	@Schema(description="Email Address of the Customer", example="John.Doe@email.com")
	@NotEmpty(message = "Email address cannot be null or empty") // Making it mandatory
	@Email(message = "Email address should be a valid value")
	private String email;

	@Schema(description="Mobile Number of the Customer", example="9867346721")
	@NotEmpty(message = "Mobile number cannot be null or empty")
	@Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
	private String mobileNumber;

	@Schema(description="Account details of the Customer")
	private AccountDto accountDto;
}
