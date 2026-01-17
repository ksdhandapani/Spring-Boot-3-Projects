package com.dhandapani.ms_accounts.dto;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(name="Response",description="Schema to hold successful response information")
@Data // Equivalent to {@code @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode}
@AllArgsConstructor
public class ResponseDto {
	@Schema(description="Status code in the response")
	private HttpStatus statusCode;
	
	@Schema(description="Status message in the response")
	private String statusMessage;
}
