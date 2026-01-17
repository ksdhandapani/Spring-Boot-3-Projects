package com.dhandapani.ms_accounts.service.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.dhandapani.ms_accounts.dto.LoanDto;

@Component
public class MsLoansFeignClientFallback implements MsLoansFeignClient {

	@Override
	public ResponseEntity<LoanDto> fetchLoanDetailByMobileNumber(String correlationId, String mobileNumber) {
		// TODO Auto-generated method stub
		return null;
	}

}
