package com.dhandapani.ms_gateway_server;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MsGatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsGatewayServerApplication.class, args);
	}

	@Bean
	public RouteLocator xyzBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				/*
				 * After the prefix path "/xyzbank/accounts", whatever path is available, assume
				 * that it is a segment. Using the same segment value or the same path, I want
				 * the request to be forwarded to the actual microservice.
				 */
				.route(p -> p.path("/xyzbank/ms-accounts/**")
						.filters(f -> f.rewritePath("/xyzbank/ms-accounts/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://MS-ACCOUNTS")) /* The microservice to which the request should be redirected. It should match the name that we have registered with the Eureka Server. The "lb" tells the Gateway Server to do Client Side Load Balancing with the help of Spring Cloud Load balancer.*/
				.route(p -> p.path("/xyzbank/ms-cards/**")
						.filters(f -> f.rewritePath("/xyzbank/ms-cards/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://MS-CARDS"))
				.route(p -> p.path("/xyzbank/ms-loans/**")
						.filters(f -> f.rewritePath("/xyzbank/ms-loans/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://MS-LOANS"))
				.build();
	}

}
