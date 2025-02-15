package com.dhandapani.spring_boot_mysql_testcontainers.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhandapani.spring_boot_mysql_testcontainers.dto.EmployeeDto;
import com.dhandapani.spring_boot_mysql_testcontainers.entity.Employee;
import com.dhandapani.spring_boot_mysql_testcontainers.exception.ResourceAlreadyExistsException;
import com.dhandapani.spring_boot_mysql_testcontainers.exception.ResourceNotFoundException;
import com.dhandapani.spring_boot_mysql_testcontainers.mapper.EmployeeMapper;
import com.dhandapani.spring_boot_mysql_testcontainers.repository.EmployeeRepository;
import com.dhandapani.spring_boot_mysql_testcontainers.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<EmployeeDto> getEmployees() {
		List<Employee> employees = this.employeeRepository.findAll();
		return employees.stream().map((employee) -> EmployeeMapper.maptoEmployeeDto(employee))
				.collect(Collectors.toList());
	}

	@Override
	public EmployeeDto getEmployeeById(Long id) {
		Employee employee = this.employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", id.toString()));
		return EmployeeMapper.maptoEmployeeDto(employee);
	}

	@Override
	public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
		Optional<Employee> foundEmployee = this.employeeRepository.findByEmail(employeeDto.getEmail());
		if (foundEmployee.isPresent()) {
			throw new ResourceAlreadyExistsException("Employee", "email", employeeDto.getEmail());
		}
		Employee employee = new Employee();
		employee.setFirstName(employeeDto.getFirstName());
		employee.setLastName(employeeDto.getLastName());
		employee.setEmail(employeeDto.getEmail());
		employee.setDeleted(employeeDto.isDeleted());
		Employee savedEmployee = this.employeeRepository.save(employee);
		return EmployeeMapper.maptoEmployeeDto(savedEmployee);
	}

	@Override
	public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
		Employee foundEmployee = this.employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", id.toString()));
		foundEmployee.setFirstName(employeeDto.getFirstName());
		foundEmployee.setLastName(employeeDto.getLastName());
		foundEmployee.setEmail(employeeDto.getEmail());
		foundEmployee.setDeleted(employeeDto.isDeleted());
		Employee updatedEmployee = this.employeeRepository.save(foundEmployee);
		return EmployeeMapper.maptoEmployeeDto(updatedEmployee);
	}

	@Override
	public boolean deleteEmployeeById(Long id) {
		Employee foundEmployee = this.employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", id.toString()));
		foundEmployee.setDeleted(true);
		this.employeeRepository.save(foundEmployee);
		return true;
	}

}
