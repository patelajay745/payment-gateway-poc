package com.example.paymentgateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private static final String[] JWT_ROUTES = {"/v1/auth/**", "/v1/merchants/**", "/v1/admin/**", "/actuator/**"};
	
	private static final String[] PUBLIC_ROUTES = {"/v1/auth/signup", "/v1/auth/login"};
	
	private static final String[] API_KEY_ROUTES =
			{"/v1/orders/**", "/v1/payments/**", "/v1/vault/**"};


//	private final ApiKeyAuthFilter apiKeyAuthFilter;

//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http.csrf(csrf -> csrf.disable())
//				.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.authorizeHttpRequests(
//						auth -> auth
//
//								        .requestMatchers("/v1/orders/**").authenticated()
//								        .anyRequest().permitAll()
//				)
//				.addFilterBefore(apiKeyAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//		return http.build();
//	}
	
	@Bean
	public SecurityFilterChain jwtChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.securityMatcher(JWT_ROUTES)
				.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth ->
						                       auth.requestMatchers(PUBLIC_ROUTES).permitAll()
								                       .anyRequest().authenticated())
				.formLogin(form -> form.disable());
		
		return http.build();
	}
}
