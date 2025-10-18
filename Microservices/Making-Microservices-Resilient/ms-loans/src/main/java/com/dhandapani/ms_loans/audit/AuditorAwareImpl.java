package com.dhandapani.ms_loans.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * @author Dhandapani Sudhakar
 * 
 * To tell JPA about currently logged-in users, we will need to provide an implementation of 
 * AuditorAware and override the getCurrentAuditor() method. 
 * And inside getCurrentAuditor(), we will need to fetch a currently logged-in user.
 */
@Component("auditorAwareImpl")
public class AuditorAwareImpl implements AuditorAware<String>{

	@Override
	public Optional<String> getCurrentAuditor() {
		return Optional.of("LOANS_MS");
	}

}
