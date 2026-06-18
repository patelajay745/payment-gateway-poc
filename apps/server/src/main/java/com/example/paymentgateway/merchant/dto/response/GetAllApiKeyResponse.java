package com.example.paymentgateway.merchant.dto.response;

import com.example.paymentgateway.common.enums.Environment;

import java.time.Instant;
import java.util.UUID;

public record GetAllApiKeyResponse(
		UUID id,
		
		String keyId,
		
		Environment environment,
		
		boolean enabled,
		
		Instant lastUsedAt,
		
		Instant rotatedAt,
		
		Instant gracePeriodExpiresAt
) {

}
