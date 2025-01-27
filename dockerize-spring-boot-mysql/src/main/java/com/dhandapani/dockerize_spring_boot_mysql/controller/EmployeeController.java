package com.dhandapani.dockerize_spring_boot_mysql.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dhandapani.dockerize_spring_boot_mysql.dto.EmployeeDto;
import com.dhandapani.dockerize_spring_boot_mysql.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping
	public ResponseEntity<List<EmployeeDto>> getEmployees() {
		List<EmployeeDto> employees = this.employeeService.getEmployees();
		return new ResponseEntity<List<EmployeeDto>>(employees, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
		EmployeeDto employee = this.employeeService.getEmployeeById(id);
		return new ResponseEntity<EmployeeDto>(employee, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
		EmployeeDto employee = this.employeeService.saveEmployee(employeeDto);
		return new ResponseEntity<EmployeeDto>(employee, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id,
			@Valid @RequestBody EmployeeDto employeeDto) {
		EmployeeDto employee = this.employeeService.updateEmployee(id, employeeDto);
		return new ResponseEntity<EmployeeDto>(employee, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long id) {
		Boolean status = this.employeeService.deleteEmployeeById(id);
		return new ResponseEntity<Boolean>(status, HttpStatus.OK);
	}

}
