package com.dhandapani.ms_accounts.controller;

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

import com.dhandapani.ms_accounts.constant.AccountConstant;
import com.dhandapani.ms_accounts.dto.AccountContactInfoDto;
import com.dhandapani.ms_accounts.dto.CustomerDto;
import com.dhandapani.ms_accounts.dto.ErrorResponseDto;
import com.dhandapani.ms_accounts.dto.ResponseDto;
import com.dhandapani.ms_accounts.service.IAccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Tag(name = "REST APIs for Accounts Microservice of DBank", description = "CRUD REST APIs for Accounts Microservice of DBank")
@RestController
@RequestMapping(path = "/api/accounts", produces = { MediaType.APPLICATION_JSON_VALUE })
/**
 * AllArgsConstructor will create a paramertized constructor and accountService
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
@Validated // For validation support
public class AccountController {

	// The keyword final is required when using @RequiredArgsConstructor
	private final IAccountService accountService;

	@Value("${build.version}")
	private String buildVersion;

	@Autowired
	private Environment environment;

	@Autowired
	private AccountContactInfoDto accountContactInfoDto;

	@Operation(summary = "Create new Account", description = "REST API to create new Customer and Account inside DBank")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "HTTP Status CREATED"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createAccount(@RequestBody @Valid CustomerDto customerDto) {
		accountService.createAccount(customerDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(HttpStatus.CREATED, AccountConstant.MESSAGE_201));

	}

	@Operation(summary = "Fetch Account Detail by Customer Mobile Number", description = "REST API to get Customer and thier Account detail by Mobile Number")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@GetMapping("/fetch")
	public ResponseEntity<CustomerDto> fetchAccountDetailByMobileNumber(
			@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
		CustomerDto customerDto = accountService.fetchAccountByMobileNumber(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(customerDto);

	}

	@Operation(summary = "Update Customer Account Detail", description = "REST API to update Customer Account detail")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(responseCode = "417", description = "Expectation Failed"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateAccountDetail(@RequestBody @Valid CustomerDto customerDto) {
		boolean isUpdated = accountService.updateAccount(customerDto);
		if (isUpdated) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(HttpStatus.OK, AccountConstant.MESSAGE_200));
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(HttpStatus.EXPECTATION_FAILED, AccountConstant.MESSAGE_417_UPDATE));
		}
	}

	@Operation(summary = "Delete Customer Account by Customer Mobile Number", description = "REST API to delete Customer Account by Customer Mobile Number")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(responseCode = "417", description = "Expectation Failed"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteAccountByMobileNumber(
			@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
		boolean isDeleted = accountService.deleteAccount(mobileNumber);
		if (isDeleted) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(HttpStatus.OK, AccountConstant.MESSAGE_200));
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
				.body(new ResponseDto(HttpStatus.EXPECTATION_FAILED, AccountConstant.MESSAGE_417_DELETE));

	}

	@Operation(summary = "Get Build Information", description = "Get Build Information that is deployed into accounts-ms")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@GetMapping("/build-info")
	public ResponseEntity<String> getBuildInfo() {
		return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
	}

	@Operation(summary = "Get Java Version", description = "Get Java Version that is installed in accounts-ms")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@GetMapping("/java-version")
	public ResponseEntity<String> getJavaInfo() {
		return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("MAVEN_HOME"));
	}

	@Operation(summary = "Get Contact Info", description = "Get Contact Information for accounts-ms")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@GetMapping("/contact-info")
	public ResponseEntity<AccountContactInfoDto> getContactInfo() {
		return ResponseEntity.status(HttpStatus.OK).body(accountContactInfoDto);
	}

}
