package com.dhandapani.ms_accounts.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.dhandapani.ms_accounts.dto.LoanDto;

@FeignClient(name = "ms-loans", fallback = MsLoansFeignClientFallback.class)
public interface MsLoansFeignClient {

	@GetMapping(path = "/api/loans/fetch", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoanDto> fetchLoanDetailByMobileNumber(
			@RequestHeader("xyzbank-correlation-id") String correlationId, @RequestParam String mobileNumber);
}
