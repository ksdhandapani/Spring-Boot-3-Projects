package com.dhandapani.ms_loans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.dhandapani.ms_loans.dto.LoanContactInfoDto;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;


/**
 * @author Dhandapani Sudhakar
 * 
 * @EnableJpaAuditing - To enable auditing in JPA via annotation configuration, 
 * auditorAwareRef - Configures the AuditorAware bean to be used to lookup the current principal
 */
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@EnableConfigurationProperties(value = {LoanContactInfoDto.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Loans Microservice REST API Documentation", 
				description = "DBank Loans Microservice REST API Documentation",
				version="v1",
				contact = @Contact(
						name = "Dhandapani Sudhakar",
						email = "ksdhandapani96@gmail.com",
						url = "https://ksdhandapani96.com"),
				license = @License(
						name = "Apache 2.0",
						url = "https://ksdhandapani96.com"
						)
				)
		)
public class MsLoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsLoansApplication.class, args);
	}

}
