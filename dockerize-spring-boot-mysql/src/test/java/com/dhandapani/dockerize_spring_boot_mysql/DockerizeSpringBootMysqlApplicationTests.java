package com.dhandapani.dockerize_spring_boot_mysql;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.config.location=classpath:application-test.properties")
class DockerizeSpringBootMysqlApplicationTests {

	@Test
	void contextLoads() {
	}

}
