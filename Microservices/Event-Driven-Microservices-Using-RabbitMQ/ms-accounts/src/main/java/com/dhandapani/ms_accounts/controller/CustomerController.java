package com.dhandapani.ms_accounts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dhandapani.ms_accounts.dto.CustomerDetailDto;
import com.dhandapani.ms_accounts.dto.ErrorResponseDto;
import com.dhandapani.ms_accounts.service.ICustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;

@Tag(name = "REST API for Customers of DBank", description = "REST API to fetch Customer Detail")
@RestController
@RequestMapping(path = "/api/customers", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
public class CustomerController {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	private final ICustomerService iCustomerService;

	public CustomerController(ICustomerService iCustomerService) {
		this.iCustomerService = iCustomerService;
	}

	@Operation(summary = "Fetch Customer Detail by Mobile Number", description = "REST API to get Customer Detail by Mobile Number")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@GetMapping(path = "/fetch")
	public ResponseEntity<CustomerDetailDto> fetchCustomerDetailByMobileNumber(
			@RequestHeader("xyzbank-correlation-id") String correlationId,
			@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
		logger.debug("fetchCustomerDetailByMobileNumber start");
		CustomerDetailDto customerDetailDto = iCustomerService.fetchCustomerDetailByMobileNumber(mobileNumber, correlationId);
		logger.debug("fetchCustomerDetailByMobileNumber end");
		return ResponseEntity.status(HttpStatus.OK).body(customerDetailDto);
	}
}
