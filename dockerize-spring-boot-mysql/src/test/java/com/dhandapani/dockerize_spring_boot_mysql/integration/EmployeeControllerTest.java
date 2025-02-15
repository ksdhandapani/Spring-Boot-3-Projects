package com.dhandapani.dockerize_spring_boot_mysql.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.dhandapani.dockerize_spring_boot_mysql.dto.EmployeeDto;
import com.dhandapani.dockerize_spring_boot_mysql.entity.Employee;
import com.dhandapani.dockerize_spring_boot_mysql.mapper.EmployeeMapper;
import com.dhandapani.dockerize_spring_boot_mysql.repository.EmployeeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest(properties = "spring.config.location=classpath:application-test.properties", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp() {
		this.employeeRepository.deleteAll();
	}

	@AfterEach
	public void tearDown() {

	}

	@Test
	@DisplayName("JUnit test for createEmployee operation")
	@Order(1)
	public void giveEmployee_whenCreateEmployee_thenReturnEmployee() throws JsonProcessingException, Exception {
		// given - precondition or setup
		EmployeeDto employee = new EmployeeDto(1L, "Richard", "Parker", "Richard.Parker@email.com", false);
		// when - action or behaviour
		ResultActions response = this.mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsBytes(employee)));
		// then -verify the output
		response.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(employee.getId().intValue())))
				.andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(employee.getLastName())))
				.andExpect(jsonPath("$.email", is(employee.getEmail())))
				.andExpect(jsonPath("$.deleted", is(employee.isDeleted())));
	}

	@Test
	@DisplayName("JUnit test for getEmployees operation")
	@Order(2)
	public void giveEmployeeList_whenGetEmployees_thenReturnEmployeeList() throws Exception {
		// given - precondition or setup
		Employee employee1 = new Employee(2L, "Richard", "Parker", "Richard.Parker@email.com", false);
		Employee employee2 = new Employee(3L, "John", "Deo", "John.Doe@email.com", false);
		Employee employee3 = new Employee(4L, "Kham", "Lam", "Kham.Lam@email.com", false);
		List<Employee> employees = List.of(employee1, employee2, employee3);
		List<Employee> savedEmployees = this.employeeRepository.saveAll(employees);
		savedEmployees.stream().map((employee) -> EmployeeMapper.maptoEmployeeDto(employee)

		).collect(Collectors.toList());
		// when - action or behaviour
		ResultActions response = this.mockMvc.perform(get("/api/employees"));
		// then -verify the output
		response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(savedEmployees.size())));
	}

	@Test
	@DisplayName("JUnit test for getEmployeeById operation")
	@Order(3)
	public void giveEmployeeId_whenGetEmployeeById_thenReturnEmployee() throws Exception {
		// given - precondition or setup
		Employee employeeToSave = new Employee(1L, "Richard", "Parker", "Richard.Parker@email.com", false);
		Employee saveddEmployee = this.employeeRepository.save(employeeToSave);
		EmployeeDto employeeDto = EmployeeMapper.maptoEmployeeDto(saveddEmployee);
		// when - action or behaviour
		ResultActions response = this.mockMvc.perform(get("/api/employees/{id}", saveddEmployee.getId()));
		// then -verify the output
		response.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(employeeDto.getId().intValue())))
				.andExpect(jsonPath("$.firstName", is(employeeDto.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(employeeDto.getLastName())))
				.andExpect(jsonPath("$.email", is(employeeDto.getEmail())))
				.andExpect(jsonPath("$.deleted", is(employeeDto.isDeleted())));
	}

	@Test
	@DisplayName("JUnit test for getEmployeeById operation - ResourceNotFoundException")
	@Order(4)
	public void giveInvalidEmployeeId_whenGetEmployeeById_thenThrowResourceNotFoundException() throws Exception {
		// given - precondition or setup
		Long invalidEmployeeId = 0L;
		this.employeeRepository.findById(invalidEmployeeId);
		// when - action or behaviour
		ResultActions response = this.mockMvc.perform(get("/api/employees/{id}", invalidEmployeeId));
		// then -verify the output
		response.andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("JUnit test for updateEmployee operation")
	@Order(5)
	public void giveEmployeeWithUpdates_whenUpdatEmployee_thenReturnUpdatedEmployee()
			throws JsonProcessingException, Exception {
		// given - precondition or setup
		Employee employeeToSave = new Employee(1L, "Richard", "Parker", "Richard.Parker@email.com", false);
		Employee employeeToUpdate = this.employeeRepository.save(employeeToSave);
		employeeToUpdate.setFirstName("Parker");
		employeeToUpdate.setLastName("Richard");
		employeeToUpdate.setEmail("Parker.Richard@email.com");
		// when - action or behaviour
		ResultActions response = this.mockMvc
				.perform(put("/api/employees/{id}", employeeToUpdate.getId()).contentType(MediaType.APPLICATION_JSON)
						.content(this.objectMapper.writeValueAsBytes(employeeToUpdate)));
		// then -verify the output
		response.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is(employeeToUpdate.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(employeeToUpdate.getLastName())))
				.andExpect(jsonPath("$.email", is(employeeToUpdate.getEmail())));
	}

	@Test
	@DisplayName("JUnit test for updateEmployee operation - ResourceNotFoundException")
	@Order(6)
	public void giveInvalidEmployeeId_whenUpdatEmployee_thenReturnUpdatedEmployee()
			throws JsonProcessingException, Exception {
		// given - precondition or setup
		Long invalidEmployeeId = 0L;
		Employee employeeToSave = new Employee(1L, "Richard", "Parker", "Richard.Parker@email.com", false);
		Employee employeeToUpdate = this.employeeRepository.save(employeeToSave);
		employeeToUpdate.setFirstName("Parker");
		employeeToUpdate.setLastName("Richard");
		employeeToUpdate.setEmail("Parker.Richard@email.com");
		// when - action or behaviour
		ResultActions response = this.mockMvc
				.perform(put("/api/employees/{id}", invalidEmployeeId).contentType(MediaType.APPLICATION_JSON)
						.content(this.objectMapper.writeValueAsBytes(employeeToUpdate)));
		// then -verify the output
		response.andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("JUnit test for deleteEmployeeById operation")
	@Order(7)
	public void giveEmployeeId_whenDeleteEmployeeById_thenReturnTrue() throws Exception {
		// given - precondition or setup
		Employee employeeToSave = new Employee(1L, "Richard", "Parker", "Richard.Parker@email.com", false);
		Employee savedEmployee = this.employeeRepository.save(employeeToSave);
		// when - action or behaviour
		ResultActions response = this.mockMvc.perform((delete("/api/employees/{id}", savedEmployee.getId())));
		// then -verify the output
		response.andDo(print()).andExpect(status().isOk());
	}

	@Test
	@DisplayName("JUnit test for deleteEmployeeById operation - ResourceNotFoundException")
	@Order(8)
	public void giveInvalidEmployeeId_whenDeleteEmployeeById_thenThrowResourceNotFoundException() throws Exception {
		// given - precondition or setup
		Long invalidEmployeeId = 0L;
		// when - action or behaviour
		ResultActions response = this.mockMvc.perform((delete("/api/employees/{id}", invalidEmployeeId)));
		// then -verify the output
		response.andDo(print()).andExpect(status().isNotFound());

	}
}
