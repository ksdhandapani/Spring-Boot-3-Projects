package com.dhandapani.ms_accounts.service;

import com.dhandapani.ms_accounts.dto.CustomerDetailDto;

public interface ICustomerService {

	CustomerDetailDto fetchCustomerDetailByMobileNumber(String mobileNumber, String correlationId);
}
