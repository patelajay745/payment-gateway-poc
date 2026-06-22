package com.example.paymentgateway.common.security;

import com.example.paymentgateway.merchant.entity.ApiKey;
import com.example.paymentgateway.merchant.repository.ApiKeyRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {
	
	private final ApiKeyRepository apiKeyRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String apiKeyHeader = request.getHeader("X-API-KEY");
		
		if (apiKeyHeader == null || !apiKeyHeader.contains(":")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		
		String[] parts = apiKeyHeader.split(":", 2);
		String keyId = parts[0];
		String rawSecret = parts[1];
		
		ApiKey apiKey = apiKeyRepository.findByKeyIdWithMerchant(keyId).orElse(null);
		
		if (apiKey == null || !apiKey.isEnabled() || !passwordEncoder.matches(rawSecret, apiKey.getKeySecretHash())) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		
		MerchantPrincipal principal = new MerchantPrincipal(apiKey.getMerchant());
		UsernamePasswordAuthenticationToken auth =
				new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		filterChain.doFilter(request, response);
	}
}
