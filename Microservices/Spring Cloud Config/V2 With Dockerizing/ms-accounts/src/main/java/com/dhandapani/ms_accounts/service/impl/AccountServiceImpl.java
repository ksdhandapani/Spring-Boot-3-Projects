package com.dhandapani.ms_accounts.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.dhandapani.ms_accounts.constant.AccountConstant;
import com.dhandapani.ms_accounts.dto.AccountDto;
import com.dhandapani.ms_accounts.dto.CustomerDto;
import com.dhandapani.ms_accounts.entity.Account;
import com.dhandapani.ms_accounts.entity.Customer;
import com.dhandapani.ms_accounts.exception.CustomerAlreadyExistsException;
import com.dhandapani.ms_accounts.exception.ResourceNotFoundException;
import com.dhandapani.ms_accounts.mapper.AccountMapper;
import com.dhandapani.ms_accounts.mapper.CustomerMapper;
import com.dhandapani.ms_accounts.repository.AccountRepository;
import com.dhandapani.ms_accounts.repository.CustomerRepository;
import com.dhandapani.ms_accounts.service.IAccountService;

import lombok.AllArgsConstructor;

@Service
/**
 * If there is a single constructor, you do not have to Autowire the
 * dependencies, Spring will do it automatically because we have a single
 * constructor which is accepting the two dependencies as parameters
 */
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

	private AccountRepository accountRepository;
	private CustomerRepository customerRepository;

	@Override
	public void createAccount(CustomerDto customerDto) {
		Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
		System.out.println("Customer Data: " + customer);
		Optional<Customer> optionalCoustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
		if (optionalCoustomer.isPresent()) {
			throw new CustomerAlreadyExistsException(
					"Customer already registered with given mobile number: " + customerDto.getMobileNumber());
		} else {
			System.out.println("Customer with mobile number " + customer.getMobileNumber() + "is not available");
		}
		Customer savedCustomer = customerRepository.save(customer);
		accountRepository.save(createNewAccount(savedCustomer));

	}

	/**
	 * 
	 * @param customer
	 * @return
	 */
	private Account createNewAccount(Customer customer) {
		Account newAccount = new Account();

		long randomAccountNumber = 1000000000L + new Random().nextInt(900000000);
		newAccount.setAccountNumber(randomAccountNumber);

		newAccount.setCustomerId(customer.getCustomerId());
		newAccount.setAccountType(AccountConstant.SAVINGS);
		newAccount.setBranchAddress(AccountConstant.ADDRESS);
		return newAccount;
	}

	@Override
	public CustomerDto fetchAccountByMobileNumber(String mobileNumber) {
		Customer foundCustomer = customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

		Account foundAccount = accountRepository.findByCustomerId(foundCustomer.getCustomerId()).orElseThrow(
				() -> new ResourceNotFoundException("Account", "customerId", foundCustomer.getCustomerId().toString()));

		CustomerDto customerDto = CustomerMapper.mapToCustomerDto(foundCustomer, new CustomerDto());
		AccountDto accountDto = AccountMapper.mapToAccountDto(foundAccount, new AccountDto());
		customerDto.setAccountDto(accountDto);
		return customerDto;

	}

	@Override
	public boolean updateAccount(CustomerDto customerDto) {
		boolean isUpdated = false;
		AccountDto accountDto = customerDto.getAccountDto();
		if (accountDto != null) {
			Account foundAccount = accountRepository.findById(accountDto.getAccountNumber())
					.orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber",
							accountDto.getAccountNumber().toString()));

			AccountMapper.mapToAccount(accountDto, foundAccount);
			Account savedAccount = accountRepository.save(foundAccount);

			Long customerId = savedAccount.getCustomerId();
			Customer foundCustomer = customerRepository.findById(customerId)
					.orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", customerId.toString()));

			CustomerMapper.mapToCustomer(customerDto, foundCustomer);
			customerRepository.save(foundCustomer);
			isUpdated = true;

		}
		return isUpdated;
	}

	@Override
	public boolean deleteAccount(String mobileNumber) {
		Customer foundCustomer = customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
		accountRepository.deleteByCustomerId(foundCustomer.getCustomerId());
		customerRepository.deleteById(foundCustomer.getCustomerId());
		return true;
	}

}
