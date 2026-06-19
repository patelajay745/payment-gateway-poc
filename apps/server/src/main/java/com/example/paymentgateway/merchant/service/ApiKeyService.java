package com.example.paymentgateway.merchant.service;

import com.example.paymentgateway.merchant.dto.request.CreateApiKeyRequest;
import com.example.paymentgateway.merchant.dto.response.CreateApiKeyResponse;
import com.example.paymentgateway.merchant.dto.response.GetAllApiKeyResponse;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface ApiKeyService {
	
	CreateApiKeyResponse createApikey(UUID merchantId, @Valid CreateApiKeyRequest request);
	
	List<GetAllApiKeyResponse> getAllApiKeys(UUID merchantId);
	
	void revokeApiKeyId(UUID merchantId, UUID apiKeyId);
}
