package com.dhandapani.ms_cards.dto;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(name="Response",description="Schema to hold successful response information")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseDto {

	@Schema(description="Status code in the response")
	private HttpStatus statusCode;
	
	@Schema(description="Status message in the response")
	private String statusMessage;
}
