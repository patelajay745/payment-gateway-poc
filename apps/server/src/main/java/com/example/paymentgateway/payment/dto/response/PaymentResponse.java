package com.example.paymentgateway.payment.dto.response;

import com.example.paymentgateway.common.entity.Money;
import com.example.paymentgateway.common.enums.PaymentMethod;
import com.example.paymentgateway.common.enums.PaymentStatus;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public record PaymentResponse(
		UUID id,
		
		UUID orderId,
		
		UUID merchantId,
		
		Money amount,
		
		PaymentStatus paymentStatus,
		
		Map<String, Object> methodDetails,
		
		String cardLastFour,
		
		String cardBrand,
		
		PaymentMethod method,
		
		String bankReference,
		
		String errorCode,
		
		String errorDescription,
		
		String refundedAmount,
		
		Instant capturedAt,
		
		Instant createdAt


) {

}
