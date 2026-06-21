package com.example.paymentgateway.payment.dto.request;

import com.example.paymentgateway.common.entity.Money;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.Map;

public record CreateOrderRequest(
		@NotNull(message = "Amount is required")
		Money amount,
		
		@Size(max = 100)
		String receipt,
		
		Map<String, Object> notes,
		
		Instant expiresAt
) {

}
