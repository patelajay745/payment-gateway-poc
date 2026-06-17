package com.example.paymentgateway.merchant.service.impl;

import com.example.paymentgateway.common.exception.ResourceNotFoundException;
import com.example.paymentgateway.merchant.dto.request.CreateApiKeyRequest;
import com.example.paymentgateway.merchant.dto.response.CreateApiKeyResponse;
import com.example.paymentgateway.merchant.entity.ApiKey;
import com.example.paymentgateway.merchant.entity.Merchant;
import com.example.paymentgateway.merchant.mapper.ApiKeyMapper;
import com.example.paymentgateway.merchant.repository.ApiKeyRepository;
import com.example.paymentgateway.merchant.repository.MerchantRepository;
import com.example.paymentgateway.merchant.service.ApiKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiKeyServiceImpl implements ApiKeyService {
	
	private final ApiKeyRepository apiKeyRepository;
	
	private final MerchantRepository merchantRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final ApiKeyMapper apiKeyMapper;
	
	@Override
	public CreateApiKeyResponse createApikey(UUID merchantId, CreateApiKeyRequest request) {
		
		Merchant merchant = merchantRepository.findById(merchantId).orElseThrow(
				() -> {
					log.warn("Merchant not found for merchantId={}", merchantId);
					return new ResourceNotFoundException("Merchant", merchantId.toString());
				});
		
		String keyId = "pay" + request.environment().name().toUpperCase() + "big_random_string";
		
		String rawSecret = "big_random_secret";
		
		log.info("Creating Api key for merchant={}, environment={}", merchantId, request.environment());
		
		ApiKey apikey = ApiKey
				                .builder()
				                .merchant(merchant)
				                .keyId(keyId)
				                .keySecretHash(passwordEncoder.encode(rawSecret))
				                .environment(request.environment())
				                .build();
		
		apikey = apiKeyRepository.save(apikey);
		
		log.info("API key created successfully keyId={}, merchantId={}", apikey.getKeyId(), merchantId);
		
		
		return apiKeyMapper.toResponse(apikey, rawSecret);
	}
}
