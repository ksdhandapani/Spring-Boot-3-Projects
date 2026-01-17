package com.dhandapani.ms_accounts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhandapani.ms_accounts.entity.Account;

import jakarta.transaction.Transactional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
	
	// Derived named method
	/**
	 * With the help our custom method, we just try to read the data, it is not going to change the data anywhere
	 * hence we don't need @Transactional annotation
	 * @param customerId
	 * @return
	 */
	Optional<Account> findByCustomerId(Long customerId);
	
	// Derived named method
	/**
	 * With the help of our custom method, we are trying to change the value in the database
	 * Using the @Transactional annotation we are telling to JPA that this method is going to make changes in DB 
	 * so execute the queries inside a transaction hence we need @Transactional annotation
	 * @param customerId
	 */
	@Transactional
	void deleteByCustomerId(Long customerId);

}
