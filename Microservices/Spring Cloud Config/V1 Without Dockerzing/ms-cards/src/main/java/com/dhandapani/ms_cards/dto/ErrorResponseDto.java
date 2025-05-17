package com.dhandapani.ms_cards.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(name="ErrorResponse",description="Schema to hold error response information")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorResponseDto {

	@Schema(description="API path invoked by client")
	private String apiPath;
	
	@Schema(description="Error code representing the error happened")
	private HttpStatus errorCode;
	
	@Schema(description="Error message representing the error happened")
	private String errorMessage;
	
	@Schema(description="Time representing when the error happened")
	private LocalDateTime errorTime;
}
