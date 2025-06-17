package com.dhandapani.ms_accounts.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dhandapani.ms_accounts.dto.AccountDto;
import com.dhandapani.ms_accounts.dto.CardDto;
import com.dhandapani.ms_accounts.dto.CustomerDetailDto;
import com.dhandapani.ms_accounts.dto.CustomerDto;
import com.dhandapani.ms_accounts.dto.LoanDto;
import com.dhandapani.ms_accounts.entity.Account;
import com.dhandapani.ms_accounts.entity.Customer;
import com.dhandapani.ms_accounts.exception.ResourceNotFoundException;
import com.dhandapani.ms_accounts.mapper.AccountMapper;
import com.dhandapani.ms_accounts.mapper.CustomerMapper;
import com.dhandapani.ms_accounts.repository.AccountRepository;
import com.dhandapani.ms_accounts.repository.CustomerRepository;
import com.dhandapani.ms_accounts.service.ICustomerService;
import com.dhandapani.ms_accounts.service.client.MsCardsFeignClient;
import com.dhandapani.ms_accounts.service.client.MsLoansFeignClient;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

	private AccountRepository accountRepository;
	private CustomerRepository customerRepository;
	private MsCardsFeignClient cardsFeignClient;
	private MsLoansFeignClient loansFeignClient;

	@Override
	public CustomerDetailDto fetchCustomerDetailByMobileNumber(String mobileNumber) {
		Customer foundCustomer = customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

		Account foundAccount = accountRepository.findByCustomerId(foundCustomer.getCustomerId()).orElseThrow(
				() -> new ResourceNotFoundException("Account", "customerId", foundCustomer.getCustomerId().toString()));

		CustomerDetailDto customerDetailDto = CustomerMapper.mapToCustomerDetailDto(foundCustomer,
				new CustomerDetailDto());
		customerDetailDto.setAccountDto(AccountMapper.mapToAccountDto(foundAccount, new AccountDto()));

		ResponseEntity<LoanDto> loanDtoResponseEntity = loansFeignClient.fetchLoanDetailByMobileNumber(mobileNumber);
		customerDetailDto.setLoanDto(loanDtoResponseEntity.getBody());

		ResponseEntity<CardDto> cardDtoResponseEntity = cardsFeignClient.fetchCardByMobileNumber(mobileNumber);
		customerDetailDto.setCardDto(cardDtoResponseEntity.getBody());

		return customerDetailDto;
	}

}
