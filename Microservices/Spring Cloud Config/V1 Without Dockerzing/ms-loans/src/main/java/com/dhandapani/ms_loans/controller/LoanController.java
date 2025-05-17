package com.dhandapani.ms_loans.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dhandapani.ms_loans.constant.LoanConstants;
import com.dhandapani.ms_loans.dto.ErrorResponseDto;
import com.dhandapani.ms_loans.dto.LoanContactInfoDto;
import com.dhandapani.ms_loans.dto.LoanDto;
import com.dhandapani.ms_loans.dto.ResponseDto;
import com.dhandapani.ms_loans.service.ILoanService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j // For logging
/**
 * @author Dhandapani Sudhakar
 */
@Tag(name = "REST APIs for Loans Microservice of DBank", description = "CRUD REST APIs for Loans Microservice of DBank")
@RestController
@RequestMapping(path = "/api/loans", produces = { MediaType.APPLICATION_JSON_VALUE })
/**
 * AllArgsConstructor will create a paramertized constructor and LoanService
 * will be the parameter, since we will have only one constructor, we do not
 * have to inject the loanService using @Autowired (Field Injection) annotation,
 * it will be automatically done (Constructor Injection)
 */
//@AllArgsConstructor
/**
 * @RequiredArgsConstructor is a Lombok annotation that generates constructors
 *                          for all final and non-null fields To generate
 *                          non-null fields, we can use the @NonNull Lombok
 *                          annotation
 */
@RequiredArgsConstructor
@Validated
public class LoanController {

	// The keyword final is required when using @RequiredArgsConstructor
	private final ILoanService loanService;

	@Value("${build.version}")
	private String buildVersion;

	@Autowired
	private Environment environment;

	@Autowired
	private LoanContactInfoDto loanContactInfoDto;

	@Operation(summary = "Create Loan for Customer by Mobile Number", description = "REST API to create loan for customer by mobile number")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "HTTP Status Created"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error"),
			@ApiResponse(responseCode = "400", description = "HTTP Status Bad Request") })
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createLoanByMobileNumber(
			@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number should be 10 digits") String mobileNumber) {
		loanService.createLoanByMobileNumber(mobileNumber);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(HttpStatus.CREATED, LoanConstants.MESSAGE_201));
	}

	@Operation(summary = "Fetch Loan Details by Mobile Number", description = "REST API to fetch loan details by mobile number")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status Successful"),
			@ApiResponse(responseCode = "404", description = "HTTP Status Not Found"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error") })
	@GetMapping("/fetch")
	public ResponseEntity<LoanDto> fetchLoanDetailByMobileNumber(
			@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number should be 10 digits") String mobileNumber) {
		LoanDto fetchedLoan = loanService.fetchLoanByMobileNumber(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(fetchedLoan);

	}

	@Operation(summary = "Update Loan Details", description = "REST API to update loan details")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status Successful"),
			@ApiResponse(responseCode = "404", description = "HTTP Status Not Found"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error"),
			@ApiResponse(responseCode = "417", description = "HTTP Status Expectation Failed") })
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateLoanDetail(@RequestBody @Valid LoanDto loanDto) {
		log.info("Received Loan Update Request: {}", loanDto);
		boolean isUpdated = loanService.updateLoan(loanDto);
		if (isUpdated) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK, LoanConstants.MESSAGE_200));

		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(HttpStatus.EXPECTATION_FAILED, LoanConstants.MESSAGE_417_UPDATE));
		}
	}

	@Operation(summary = "Delete Loan Detail by Mobile Number", description = "REST API to delete loan details by mobile number")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status Successful"),
			@ApiResponse(responseCode = "404", description = "HTTP Status Not Found"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error"),
			@ApiResponse(responseCode = "417", description = "HTTP Status Expectation Failed") })
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteLoanByMobileNumber(
			@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number should be 10 digits") String mobileNumber) {
		boolean isDeleted = loanService.deleteLoanByMobileNumber(mobileNumber);
		if (isDeleted) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK, LoanConstants.MESSAGE_200));

		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(HttpStatus.EXPECTATION_FAILED, LoanConstants.MESSAGE_417_DELETE));
		}
	}

	@Operation(summary = "Get Build Information", description = "Get Build Information that is deployed into loans-ms")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@GetMapping("/build-info")
	public ResponseEntity<String> getBuildInfo() {
		return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
	}

	@Operation(summary = "Get Java Version", description = "Get Java Version that is installed in loans-ms")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@GetMapping("/java-version")
	public ResponseEntity<String> getJavaInfo() {
		return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("MAVEN_HOME"));
	}

	@Operation(summary = "Get Contact Info", description = "Get Contact Information for loans-ms")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@GetMapping("/contact-info")
	public ResponseEntity<LoanContactInfoDto> getContactInfo() {
		return ResponseEntity.status(HttpStatus.OK).body(loanContactInfoDto);
	}

}
