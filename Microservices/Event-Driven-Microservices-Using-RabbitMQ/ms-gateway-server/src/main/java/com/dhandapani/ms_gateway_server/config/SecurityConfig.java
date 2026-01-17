package com.dhandapani.ms_gateway_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import reactor.core.publisher.Mono;

/* Security Configuration class for the API Gateway */
@Configuration /* Marks this class as a configuration class for Spring */
/*
 * Spring Cloud Gateway uses the Spring Reactive Module, so we should use
 * the @EnableWebFluxSecurity annotation. For standard Spring Boot web
 * applications, @EnableWebSecurity is required.
 */
@EnableWebFluxSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityWebFilterChain springSecurityFilterchain(ServerHttpSecurity serverHttpSecurity) {
		serverHttpSecurity
			.authorizeExchange(exchanges -> exchanges
					.pathMatchers(HttpMethod.GET).permitAll() /* Allow all GET requests without authentication */
					.pathMatchers("/xyzbank/ms-accounts/**").hasRole("ACCOUNTS")
					.pathMatchers("/xyzbank/ms-cards/**").hasRole("CARDS")
					.pathMatchers("/xyzbank/ms-loans/**").hasRole("LOANS")
			)
			.oauth2ResourceServer(oauth2ResourceServerSpec -> oauth2ResourceServerSpec
				.jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthoritiesExtractor())) /* With this we are telling Spring Security framework, please use the default configurations related to the JWT Tokens */
			);
		serverHttpSecurity.csrf(csrfSpec -> csrfSpec.disable()); /* Disable CSRF protection as we are using JWT tokens which are not vulnerable to CSRF attacks, CSRF is required when browser is involved */
		return serverHttpSecurity.build();
	}
	
	private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor(){
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
		return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
	}

}
