package com.dhandapani.ms_accounts.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dhandapani.ms_accounts.dto.LoanDto;

@FeignClient(name = "ms-loans")
public interface MsLoansFeignClient {

	@GetMapping(path = "/api/loans/fetch", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoanDto> fetchLoanDetailByMobileNumber(@RequestParam String mobileNumber);
}
