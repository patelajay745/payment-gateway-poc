package com.example.paymentgateway.merchant.controller;

import com.example.paymentgateway.merchant.dto.request.CreateApiKeyRequest;
import com.example.paymentgateway.merchant.dto.response.CreateApiKeyResponse;
import com.example.paymentgateway.merchant.dto.response.GetAllApiKeyResponse;
import com.example.paymentgateway.merchant.service.ApiKeyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/merchants/{merchantId}/api-key")
@RequiredArgsConstructor
public class ApiKeyController {
	
	private final ApiKeyService apiKeyService;
	
	@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<CreateApiKeyResponse> createApiKeyUsingFormData(@PathVariable UUID merchantId,
	                                                                      @ModelAttribute @Valid CreateApiKeyRequest request) {
		return createApiKey(merchantId, request);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CreateApiKeyResponse> createApiKeyUsingJsonData(@PathVariable UUID merchantId,
	                                                                      @RequestBody @Valid CreateApiKeyRequest request) {
		return createApiKey(merchantId, request);
	}
	
	@GetMapping
	public ResponseEntity<List<GetAllApiKeyResponse>> getAllApiKeys(@PathVariable UUID merchantId) {
		return ResponseEntity.status(200).body(apiKeyService.getAllApiKeys(merchantId));
	}
	
	private ResponseEntity<CreateApiKeyResponse> createApiKey(UUID merchantId, CreateApiKeyRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(apiKeyService.createApikey(merchantId, request));
	}
	
	@DeleteMapping("/{apiKeyId}")
	public ResponseEntity<Void> revokeApikey(@PathVariable UUID merchantId, @PathVariable UUID apiKeyId) {
		
		apiKeyService.revokeApiKeyId(merchantId, apiKeyId);
		
		return ResponseEntity.status(204).build();
	}
}
