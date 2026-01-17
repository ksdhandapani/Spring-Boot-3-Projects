package com.dhandapani.ms_gateway_server;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import reactor.core.publisher.Mono;

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
						.filters(
								f -> f.rewritePath("/xyzbank/ms-accounts/(?<segment>.*)", "/${segment}")
										.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
										.circuitBreaker(config -> config.setName("ms-accounts-circuit-breaker")
												.setFallbackUri("forward:/contactSupport")))
						.uri("lb://MS-ACCOUNTS")) /*
													 * The microservice to which the request should be redirected. It
													 * should match the name that we have registered with the Eureka
													 * Server. The "lb" tells the Gateway Server to do Client Side Load
													 * Balancing with the help of Spring Cloud Load balancer.
													 */
				.route(p -> p.path("/xyzbank/ms-cards/**")
						.filters(f -> f.rewritePath("/xyzbank/ms-cards/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								/*
								 * Rate Limiter filter configuration. Here we are setting the RedisRateLimiter
								 * and the KeyResolver
								 */
								.requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
										.setKeyResolver(userKeyResolver())))
						.uri("lb://MS-CARDS"))
				.route(p -> p.path("/xyzbank/ms-loans/**")
						.filters(f -> f.rewritePath("/xyzbank/ms-loans/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.retry(retryConfig -> retryConfig.setRetries(3).setMethods(HttpMethod.GET)
										.setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)))
						.uri("lb://MS-LOANS"))
				.build();
	}

	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
				/*
				 * This is the time limit that the circuit breaker must follow; we are setting
				 * the timeout duration to 4 seconds. With this, our circuit breaker pattern is
				 * going to wait for a maximum of 4 seconds whenever it is trying to wait for a
				 * particular operation to complete.
				 */
				.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build()).build());
	}

	/**
	 * Here we are configuring the RedisRateLimiter. The three parameters that we are
	 * passing to the RedisRateLimiter constructor are:
	 * 
	 * 1. Replenish Rate: It is the number of requests that we want to allow per
	 * second. Here we are allowing only one request per second.
	 * 
	 * 2. Burst Capacity: It is the maximum number of requests that can be allowed
	 * during a burst. Here we are allowing only one request.
	 * 
	 * 3. Requested Tokens: It is the number of tokens that should be requested for
	 * each request. Here we are requesting one token for each request.
	 */
	@Bean
	public RedisRateLimiter redisRateLimiter() {
		return new RedisRateLimiter(1, 1, 1);
	}

	/**
	 * We are fetching the request header with the name 'user' from the request, and
	 * based on the header value, we are creating a KeyResolver. If someone is not
	 * sending the 'user' header in the request, we are assigning a default value,
	 * which is 'anonymous'.
	 */
	@Bean
	KeyResolver userKeyResolver() {
		return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("user"))
				.defaultIfEmpty("anonymous");
	}

}
