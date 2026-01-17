package com.dhandapani.ms_accounts.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.dhandapani.ms_accounts.dto.CardDto;

@FeignClient(name = "ms-cards", fallback = MsCardsFeignClientFallback.class)
public interface MsCardsFeignClient {

	@GetMapping(path = "/api/cards/fetch", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CardDto> fetchCardByMobileNumber(
			@RequestHeader("xyzbank-correlation-id") String correlationId, @RequestParam String mobileNumber);
}
