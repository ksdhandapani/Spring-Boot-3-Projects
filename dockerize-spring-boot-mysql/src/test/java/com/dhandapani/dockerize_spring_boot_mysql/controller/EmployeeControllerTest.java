package com.dhandapani.dockerize_spring_boot_mysql.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.dhandapani.dockerize_spring_boot_mysql.dto.EmployeeDto;
import com.dhandapani.dockerize_spring_boot_mysql.exception.ResourceNotFoundException;
import com.dhandapani.dockerize_spring_boot_mysql.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class EmployeeControllerTest {

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * @MockMvc annotation refers to the MockMvc object which is used to test Spring
	 *          controllers by simulating HTTP requests and responses without
	 *          actually starting a full web server, allowing you to perform
	 *          isolated tests on your controller endpoints and verify their
	 *          behavior with mock data and expected results
	 */
	@Autowired
	private MockMvc mockMvc;

	/**
	 * @MockBean annotation tells Spring to create mock instance of EmployeeService
	 *           and add it to the application context so that it is injected to the
	 *           EmployeeController.
	 */
	@MockBean
	private EmployeeService employeeService;

	private EmployeeDto employee;

	@BeforeEach
	public void setUp() {
		employee = new EmployeeDto(1L, "John", "Doe", "John.Doe@email.com", false);
	}

	@AfterEach
	public void tearDown() {
		employee = null;
	}

	@Test
	@DisplayName("JUnit test for getEmployees operation")
	public void givenEmployeeList_whenGetEmployees_thenReturnEmployeesList() {
		try {
			// given - precondition or setup
			// when - action or behaviour
			// return - verify the output

			// given - precondition or setup
			EmployeeDto e1 = new EmployeeDto(1L, "John", "Doe", "John.Doe@email.com", false);
			EmployeeDto e2 = new EmployeeDto(1L, "Richard", "Parker", "Richard.Parker@email.com", false);
			List<EmployeeDto> employees = List.of(e1, e2);
			given(employeeService.getEmployees()).willReturn(employees);
			// when - action or behaviour
			ResultActions response = mockMvc.perform(get("/api/employees"));
			// return - verify the output
			response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(employees.size())));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Test
	@DisplayName("JUnit test for createEmployee operation")
	public void givenEmployee_whenCreateEmployee_thenReturnEmployee() {
		try {
			// given - precondition or setup
			given(employeeService.saveEmployee(any(EmployeeDto.class))).willReturn(employee);
			// when - action or behaviour
			ResultActions response = mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON)
					.content(this.objectMapper.writeValueAsBytes(employee)));
			// return - verify the output
			response.andDo(print()).andExpect(status().isCreated())
					.andExpect(jsonPath("$.id", is(employee.getId().intValue())))
					.andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
					.andExpect(jsonPath("$.lastName", is(employee.getLastName())))
					.andExpect(jsonPath("$.email", is(employee.getEmail())))
					.andExpect(jsonPath("$.deleted", is(employee.isDeleted())));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	@DisplayName("JUnit test for getEmployeeById operation")
	public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployee() {
		try {
			// given - precondition or setup
			given(employeeService.getEmployeeById(employee.getId())).willReturn(employee);
			// when - action or behaviour
			ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee.getId()));
			// return - verify the output
			response.andDo(print()).andExpect(status().isOk())
					.andExpect(jsonPath("$.id", is(employee.getId().intValue())))
					.andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
					.andExpect(jsonPath("$.lastName", is(employee.getLastName())))
					.andExpect(jsonPath("$.email", is(employee.getEmail())))
					.andExpect(jsonPath("$.deleted", is(employee.isDeleted())));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	@DisplayName("JUnit test for getEmployeeById operation - ResourceNotFoundException")
	public void givenInvalidEmployeeId_whenGetEmployeeById_thenThrowsResourceNotFoundException() throws Exception {
		// given - precondition or setup
		given(employeeService.getEmployeeById(employee.getId())).willThrow(ResourceNotFoundException.class);
		// when - action or behaviour
		ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee.getId()));
		// return - verify the output
		response.andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("JUnit test for updateEmployee operation")
	public void givenEmployeeWithUpdates_whenUpdateEmployee_thenReturnUpdatedEmployee() {
		try {
			// given - precondition or setup
			EmployeeDto employeeForUpdate = new EmployeeDto(employee.getId(), "Doe", "John", "Doe.John@email.com",
					false);
			given(employeeService.updateEmployee(anyLong(), any(EmployeeDto.class))).willReturn(employeeForUpdate);
			// when - action or behaviour
			ResultActions response = mockMvc
					.perform(put("/api/employees/{id}", employee.getId()).contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsBytes(employeeForUpdate)));
			// return - verify the output
			response.andDo(print()).andExpect(status().isOk())
					.andExpect(jsonPath("$.firstName", is(employeeForUpdate.getFirstName())))
					.andExpect(jsonPath("$.lastName", is(employeeForUpdate.getLastName())))
					.andExpect(jsonPath("$.email", is(employeeForUpdate.getEmail())))
					.andExpect(jsonPath("$.deleted", is(employeeForUpdate.isDeleted())));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	@DisplayName("JUnit test for updateEmployee operation - ResourceNotFoundException")
	public void givenInvalidEmployeeWithUpdates_whenUpdateEmployee_thenThrowResourceNotFoundException()
			throws JsonProcessingException, Exception {

		// given - precondition or setup
		EmployeeDto employeeForUpdate = new EmployeeDto(employee.getId(), "Doe", "John", "Doe.John@email.com", false);
		given(employeeService.updateEmployee(anyLong(), any(EmployeeDto.class)))
				.willThrow(ResourceNotFoundException.class);
		// when - action or behaviour
		ResultActions response = mockMvc.perform(put("/api/employees/{id}", employee.getId())
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(employeeForUpdate)));
		// return - verify the output
		response.andDo(print()).andExpect(status().isNotFound());

	}

	@Test
	@DisplayName("JUnit test for deleteEmployeeById operation")
	public void givenEmployeeId_whenDeleteEmployeeById_thenReturnTrue() {
		try {
			// given - precondition or setup
			given(employeeService.deleteEmployeeById(employee.getId())).willReturn(true);
			// when - action or behaviour
			ResultActions response = mockMvc.perform(delete("/api/employees/{id}", employee.getId()));
			// return - verify the output
			response.andDo(print()).andExpect(status().isOk());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	@DisplayName("JUnit test for deleteEmployeeById operation - ResourceNotFoundException")
	public void givenInvalidEmployeeId_whenDeleteEmployeeById_thenThrowResourceNotFoundException() throws Exception {
		// given - precondition or setup
		given(employeeService.deleteEmployeeById(employee.getId())).willThrow(ResourceNotFoundException.class);
		// when - action or behaviour
		ResultActions response = mockMvc.perform(delete("/api/employees/{id}", employee.getId()));
		// return - verify the output
		response.andDo(print()).andExpect(status().isNotFound());
	}

}
