package com.dhandapani.ms_loans.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Dhandapani Sudhakar
 */
@Schema(name = "ErrorResponse", description = "Schema to hold error response")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorResponseDto {

	@Schema(description = "API path invoked by client")
	private String apiPath;

	@Schema(description = "Error code representing the error happened")
	private HttpStatus errorCode;

	@Schema(description = "Error message representing the error happened")
	private String errorMessage;

	@Schema(description = "Time representing when the error happened")
	private LocalDateTime erroTime;

}
