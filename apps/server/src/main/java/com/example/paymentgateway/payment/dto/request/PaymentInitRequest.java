package com.example.paymentgateway.payment.dto.request;

import com.example.paymentgateway.common.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;

import java.util.Map;
import java.util.UUID;

public record PaymentInitRequest(
		@NotNull(message = "orderId is required")
		UUID orderId,
		
		@NotNull(message = "method is required")
		PaymentMethod method,
		
		Map<String, Object> methodDetails

) {

}
