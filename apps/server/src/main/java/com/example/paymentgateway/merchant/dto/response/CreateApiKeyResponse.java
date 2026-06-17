package com.example.paymentgateway.merchant.dto.response;

import java.util.UUID;

public record CreateApiKeyResponse
		(
				UUID id,
				
				String keyId,
				
				String keySecret,
				
				String environment
		
		) {
	
}
