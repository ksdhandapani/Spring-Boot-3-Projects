package com.dhandapani.ms_cards.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dhandapani.ms_cards.constant.CardConstants;
import com.dhandapani.ms_cards.dto.CardContactInfoDto;
import com.dhandapani.ms_cards.dto.CardDto;
import com.dhandapani.ms_cards.dto.ErrorResponseDto;
import com.dhandapani.ms_cards.dto.ResponseDto;
import com.dhandapani.ms_cards.service.ICardService;

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

@Tag(name = "REST APIs for Cards Microservice of DBank", description = "CRUD REST APIs for Cards Microservice of DBank")
@RestController
@RequestMapping(path = "/api/cards", produces = MediaType.APPLICATION_JSON_VALUE)
/**
 * AllArgsConstructor will create a paramertized constructor and cardService
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
public class CardController {
	
	private static final Logger logger = LoggerFactory.getLogger(CardController.class);

	// The keyword final is required when using @RequiredArgsConstructor
	private final ICardService cardService;

	@Value("${build.version}")
	private String buildVersion;

	@Autowired
	private Environment environment;

	@Autowired
	private CardContactInfoDto cardContactInfoDto;

	@Operation(summary = "Create Card for Customer by Mobile Number", description = "REST API to create card for customer by mobile number")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "HTTP Status Created"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error"),
			@ApiResponse(responseCode = "400", description = "HTTP Status Bad Request") })
	@PostMapping(path = "/create")
	public ResponseEntity<ResponseDto> createNewCardByMobileNumber(
			@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits") String mobileNumber) {
		cardService.createCardByMobileNumber(mobileNumber);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(HttpStatus.CREATED, CardConstants.MESSAGE_201));

	}

	@Operation(summary = "Fetch Card Details by Mobile Number", description = "REST API to fetch card details by mobile number")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status Successful"),
			@ApiResponse(responseCode = "404", description = "HTTP Status Not Found"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error") })
	@GetMapping(path = "/fetch")
	public ResponseEntity<CardDto> fetchCardByMobileNumber(
			@RequestHeader("xyzbank-correlation-id") String correlationId,
			@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits") String mobileNumber) {
		logger.debug("fetchCardByMobileNumber start");
		CardDto cardDto = cardService.fetchCardByMobileNumber(mobileNumber);
		logger.debug("fetchCardByMobileNumber end");
		return ResponseEntity.status(HttpStatus.CREATED).body(cardDto);
	}

	@Operation(summary = "Update Card Details", description = "REST API to update card details")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status Successful"),
			@ApiResponse(responseCode = "404", description = "HTTP Status Not Found"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error"),
			@ApiResponse(responseCode = "417", description = "HTTP Status Expectation Failed") })
	@PutMapping(path = "/update")
	public ResponseEntity<ResponseDto> updateCard(@RequestBody @Valid CardDto cardDto) {
		boolean isUpdated = cardService.updateCard(cardDto);
		if (isUpdated) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK, CardConstants.MESSAGE_200));
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(HttpStatus.EXPECTATION_FAILED, CardConstants.MESSAGE_417_UPDATE));
		}
	}

	@Operation(summary = "Delete Card Detail by Mobile Number", description = "REST API to card loan details by mobile number")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status Successful"),
			@ApiResponse(responseCode = "404", description = "HTTP Status Not Found"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error"),
			@ApiResponse(responseCode = "417", description = "HTTP Status Expectation Failed") })
	@DeleteMapping(path = "/delete")
	public ResponseEntity<ResponseDto> deleteCardByMobileNumber(
			@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits") String mobileNumber) {
		boolean isDeleted = cardService.deleteCardByMobileNumber(mobileNumber);
		if (isDeleted) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK, CardConstants.MESSAGE_200));
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(HttpStatus.EXPECTATION_FAILED, CardConstants.MESSAGE_417_DELETE));
		}
	}

	@Operation(summary = "Get Build Information", description = "Get Build Information that is deployed into cards-ms")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@GetMapping("/build-info")
	public ResponseEntity<String> getBuildInfo() {
		return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
	}

	@Operation(summary = "Get Java Version", description = "Get Java Version that is installed in cards-ms")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@GetMapping("/java-version")
	public ResponseEntity<String> getJavaInfo() {
		return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("MAVEN_HOME"));
	}

	@Operation(summary = "Get Contact Info", description = "Get Contact Information for cards-ms")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@GetMapping("/contact-info")
	public ResponseEntity<CardContactInfoDto> getContactInfo() {
		return ResponseEntity.status(HttpStatus.OK).body(cardContactInfoDto);
	}
}
