package com.dhandapani.spring_boot_mysql_testcontainers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhandapani.spring_boot_mysql_testcontainers.entity.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Optional<Employee> findByEmail(String email);
}
