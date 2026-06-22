package com.example.paymentgateway.config;

import com.example.paymentgateway.common.security.ApiKeyAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final ApiKeyAuthFilter apiKeyAuthFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(
						auth -> auth
								        .requestMatchers("/v1/merchants/**").permitAll()
								        .requestMatchers("/v1/orders/**").authenticated()
								        .anyRequest().permitAll()
				)
				.addFilterBefore(apiKeyAuthFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
}
