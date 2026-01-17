package com.dhandapani.ms_message.functions;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dhandapani.ms_message.dto.AccountsMessageDto;

@Configuration
public class MessageFunctions {

	private static final Logger log = LoggerFactory.getLogger(MessageFunctions.class);

	@Bean
	public Function<AccountsMessageDto, AccountsMessageDto> sendEmail() {
		return accountsMessageDto -> {
			log.info("Sending Email with the details: {}", accountsMessageDto);
			return accountsMessageDto;
		};
	}

	@Bean
	public Function<AccountsMessageDto, Long> sendSms() {
		return accountsMessageDto -> {
			log.info("Sending SMS with the details: {}", accountsMessageDto);
			return accountsMessageDto.accountNumber();
		};
	}

}
