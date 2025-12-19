package com.dhandapani.ms_accounts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhandapani.ms_accounts.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

	// Derived named method
	Optional<Customer> findByMobileNumber(String mobileNumber);
}
