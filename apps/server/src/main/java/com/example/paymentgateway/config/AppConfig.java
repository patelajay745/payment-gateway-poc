package com.example.paymentgateway.config;

import com.example.paymentgateway.common.security.MerchantUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(MerchantUserDetailsService merchantUserDetailsService,
	                                                   PasswordEncoder passwordEncoder) {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(merchantUserDetailsService);
		
		provider.setPasswordEncoder(passwordEncoder);
		
		return new ProviderManager(provider);
	}
}
