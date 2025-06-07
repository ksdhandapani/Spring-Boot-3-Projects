package com.dhandapani.ms_accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.dhandapani.ms_accounts.dto.AccountContactInfoDto;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {AccountContactInfoDto.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts Microservice REST API Documentation", 
				description = "DBank Accounts Microservice REST API Documentation", 
				version = "v1", 
				contact = @Contact(
						name = "Dhandapani Sudhakar", 
						email = "ksdhandapani96@gmail.com", 
						url = "https://ksdhandapani96.com"
						),
				license = @License(
						name = "Apache 2.0",
						url = "https://ksdhandapani96.com")
				)
		)
public class MsAccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAccountsApplication.class, args);
	}

}
