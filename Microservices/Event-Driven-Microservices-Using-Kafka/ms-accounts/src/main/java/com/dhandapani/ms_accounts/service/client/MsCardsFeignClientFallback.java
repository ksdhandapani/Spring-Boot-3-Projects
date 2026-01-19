package com.dhandapani.ms_accounts.service.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.dhandapani.ms_accounts.dto.CardDto;

@Component
public class MsCardsFeignClientFallback implements MsCardsFeignClient {

	@Override
	public ResponseEntity<CardDto> fetchCardByMobileNumber(String correlationId, String mobileNumber) {
		// TODO Auto-generated method stub
		return null;
	}

}
