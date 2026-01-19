package com.dhandapani.ms_accounts.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String>{

	@Override
	public Optional<String> getCurrentAuditor() {
		// This value will be used for created_by and updated_by fields
		return Optional.of("ACCOUNT_MS"); 
	}

}
