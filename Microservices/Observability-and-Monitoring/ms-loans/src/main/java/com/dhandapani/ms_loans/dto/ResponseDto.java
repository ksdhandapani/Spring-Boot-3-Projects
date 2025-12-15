package com.dhandapani.ms_loans.dto;

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
@Schema(name = "Response", description = "Schema to hold successful response")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseDto {

	@Schema(description = "Code respresent the response status")
	private HttpStatus statusCode;

	@Schema(description = "Status messsage in the response")
	private String statusMessage;
}
