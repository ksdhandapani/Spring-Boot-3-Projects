package com.dhandapani.dockerize_spring_boot_mysql.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dhandapani.dockerize_spring_boot_mysql.dto.EmployeeDto;
import com.dhandapani.dockerize_spring_boot_mysql.entity.Employee;
import com.dhandapani.dockerize_spring_boot_mysql.exception.ResourceAlreadyExistsException;
import com.dhandapani.dockerize_spring_boot_mysql.exception.ResourceNotFoundException;
import com.dhandapani.dockerize_spring_boot_mysql.mapper.EmployeeMapper;
import com.dhandapani.dockerize_spring_boot_mysql.repository.EmployeeRepository;
import com.dhandapani.dockerize_spring_boot_mysql.service.impl.EmployeeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	private EmployeeDto employeeDto;

	@BeforeEach
	public void setUp() {
		employeeDto = new EmployeeDto(1L, "John", "Doe", "John.Doe@email.com", false);
	}

	@AfterEach
	public void tearDown() {
		employeeDto = null;
	}

	// given - precondition or set up
	// when - action or the behaviour
	// then - verify the output

	@Test
	@DisplayName("JUnit test for saveEmployee operation")
	public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
		// given - precondition or set up
		Employee employee = EmployeeMapper.maptoEmployee(employeeDto);
		given(employeeRepository.findByEmail(employeeDto.getEmail())).willReturn(Optional.empty());
		given(employeeRepository.save(any(Employee.class))).willReturn(employee);
		// when - action or the behaviour
		EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto);
		// then - verify the output
		assertThat(savedEmployee).isNotNull();
	}

	@Test
	@DisplayName("JUnit test for saveEmployee operation - ResourceAlreadyExists Exception")
	public void givenEmployeeObject_whenSaveEmployee_thenThrowResourceAlreadyExistsException() {
		// given - precondition or set up
		Employee employee = EmployeeMapper.maptoEmployee(employeeDto);
		given(employeeRepository.findByEmail(employeeDto.getEmail())).willReturn(Optional.of(employee));
		// when - action or the behaviour
		assertThrows(ResourceAlreadyExistsException.class, () -> {
			employeeService.saveEmployee(employeeDto);
		});
		// then - verify the output
		verify(employeeRepository, never()).save(any(Employee.class));
	}

	@Test
	@DisplayName("JUnit test for getEmployees operation")
	public void givenEmployeeList_whenGetEmployees_thenReturnEmployeeList() {
		// given - precondition or set up
		EmployeeDto employeeDto1 = new EmployeeDto(1L, "John", "Doe", "John.Doe@email.com", false);
		EmployeeDto employeeDto2 = new EmployeeDto(2L, "Gabriel", "Farrel", "Gabriel.Farrel@email.com", false);
		Employee employee1 = EmployeeMapper.maptoEmployee(employeeDto1);
		Employee employee2 = EmployeeMapper.maptoEmployee(employeeDto2);
		given(employeeRepository.findAll()).willReturn(List.of(employee1, employee2));
		// when - action or the behaviour
		List<EmployeeDto> employees = employeeService.getEmployees();
		// then - verify the output
		assertThat(employees).isNotNull();
		assertThat(employees.size()).isEqualTo(2);
	}

	@Test
	@DisplayName("JUnit test for getEmployees operation - Empty List (Negative Scenario)")
	public void givenEmployeeEmployeeList_whenGetEmployees_thenReturnEmptyEmployeeList() {
		// given - precondition or set up
		given(employeeRepository.findAll()).willReturn(Collections.emptyList());
		// when - action or the behaviour
		List<EmployeeDto> employees = employeeService.getEmployees();
		// then - verify the output
		assertThat(employees).isEmpty();
		assertThat(employees.size()).isEqualTo(0);
	}

	@Test
	@DisplayName("JUnit test for getEmployeeById operation")
	public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployee() {
		// given - precondition or set up
		Employee employee = EmployeeMapper.maptoEmployee(employeeDto);
		given(employeeRepository.findById(employeeDto.getId())).willReturn(Optional.of(employee));
		// when - action or the behaviour
		EmployeeDto foundEmployee = employeeService.getEmployeeById(employeeDto.getId());
		// then - verify the output
		assertThat(foundEmployee).isNotNull();
		assertThat(foundEmployee.getId()).isEqualTo(employeeDto.getId());

	}

	@Test
	@DisplayName("JUnit test for getEmployeeById operation - ResourceNotFoundException")
	public void givenInvalidEmployeeId_whenGetEmployeeById_thenThrowsResourceNotFoundException() {
		// given - precondition or set up
		given(employeeRepository.findById(employeeDto.getId())).willReturn(Optional.empty());
		// when - action or the behaviour
		assertThrows(ResourceNotFoundException.class, () -> {
			employeeService.getEmployeeById(employeeDto.getId());
		});
	}

	@Test
	@DisplayName("JUnit test for deleteEmployeeById operation")
	public void givenEmployeeId_whenDeleteEmployeeById_thenReturnTrue() {
		// given - precondition or set up
		Employee employee = EmployeeMapper.maptoEmployee(employeeDto);
		given(employeeRepository.findById(employeeDto.getId())).willReturn(Optional.of(employee));
		employee.setDeleted(true);
		given(employeeRepository.save(employee)).willReturn(employee);
		// when - action or the behaviour
		Boolean status = employeeService.deleteEmployeeById(employeeDto.getId());
		// then - verify the output
		assertTrue(status);
		verify(employeeRepository, times(1)).save(employee);
	}

	@Test
	@DisplayName("JUnit test for deleteEmployeeById operation - ResourceNotFoundException")
	public void givenInvalidEmployeeId_whenDeleteEmployeeById_thenThrowsResourceNotFoundException() {
		// given - precondition or set up
		given(employeeRepository.findById(employeeDto.getId())).willReturn(Optional.empty());
		// when - action or the behaviour
		assertThrows(ResourceNotFoundException.class, () -> {
			employeeService.deleteEmployeeById(employeeDto.getId());
		});
	}

	@Test
	@DisplayName("JUnit test for updateEmployee operation")
	public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee() {
		// given - precondition or set up
		Employee employee = EmployeeMapper.maptoEmployee(employeeDto);
		given(employeeRepository.findById(employeeDto.getId())).willReturn(Optional.of(employee));
		employee.setFirstName("Doe");
		employee.setLastName("John");
		employee.setEmail("Doe.John@email.com");
		given(employeeRepository.save(employee)).willReturn(employee);
		// when - action or the behaviour
		EmployeeDto updatedEmployee = employeeService.updateEmployee(employeeDto.getId(), employeeDto);
		// then - verify the output
		assertThat(updatedEmployee).isNotNull();
		assertThat(updatedEmployee.getFirstName()).isEqualTo(employee.getFirstName());
		assertThat(updatedEmployee.getLastName()).isEqualTo(employee.getLastName());
		assertThat(updatedEmployee.getEmail()).isEqualTo(employee.getEmail());
	}

	@Test
	@DisplayName("JUnit test for updateEmployee operation - ResourceNotFoundException")
	public void givenInvalidUpdatedEmployee_whenUpdateEmployee_thenThrowResourceNotFoundException() {
		// given - precondition or set up
		given(employeeRepository.findById(employeeDto.getId())).willReturn(Optional.empty());
		// when - action or the behaviour
		assertThrows(ResourceNotFoundException.class, () -> {
			employeeService.updateEmployee(employeeDto.getId(), employeeDto);
		});
	}
}
