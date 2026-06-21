package com.example.paymentgateway.payment.dto.response;

import com.example.paymentgateway.common.entity.Money;
import com.example.paymentgateway.common.enums.OrderStatus;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public record CreateOrderResponse(
		UUID id,
		
		UUID merchantId,
		
		String receipt,
		
		Money amount,
		
		OrderStatus status,
		
		Integer attempts,
		
		Map<String, Object> notes,
		
		Instant expiresAt,
		
		Instant createdAt
) {

}
