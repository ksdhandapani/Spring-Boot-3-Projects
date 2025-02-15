package com.dhandapani.spring_boot_mysql_testcontainers.mapper;

import com.dhandapani.spring_boot_mysql_testcontainers.dto.EmployeeDto;
import com.dhandapani.spring_boot_mysql_testcontainers.entity.Employee;

public class EmployeeMapper {

	public static EmployeeDto maptoEmployeeDto(Employee employee) {
		return new EmployeeDto(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmail(),
				employee.isDeleted());
	}

	public static Employee maptoEmployee(EmployeeDto employeeDto) {
		return new Employee(employeeDto.getId(), employeeDto.getFirstName(), employeeDto.getLastName(),
				employeeDto.getEmail(), employeeDto.isDeleted());
	}

}
