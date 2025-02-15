package com.dhandapani.spring_boot_mysql_testcontainers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class EmployeeDto {
	private Long id;
	@NotEmpty(message = "First Name should not be empty or null")
	private String firstName;
	@NotEmpty(message = "Last Name should not be empty or null")
	private String lastName;
	@NotEmpty(message = "Email should not be empty or null")
	@Email
	private String email;
	private boolean isDeleted;

	public EmployeeDto(Long id, @NotEmpty(message = "First Name should not be empty or null") String firstName,
			@NotEmpty(message = "Last Name should not be empty or null") String lastName,
			@NotEmpty(message = "Email should not be empty or null") @Email String email, boolean isDeleted) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.isDeleted = isDeleted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public EmployeeDto() {
		super();
	}

}
