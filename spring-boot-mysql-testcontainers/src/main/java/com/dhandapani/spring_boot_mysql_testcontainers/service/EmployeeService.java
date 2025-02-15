package com.dhandapani.spring_boot_mysql_testcontainers.service;

import java.util.List;

import com.dhandapani.spring_boot_mysql_testcontainers.dto.EmployeeDto;


public interface EmployeeService {

	public List<EmployeeDto> getEmployees();

	public EmployeeDto getEmployeeById(Long id);

	public EmployeeDto saveEmployee(EmployeeDto employeeDto);

	public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto);

	public boolean deleteEmployeeById(Long id);

}
