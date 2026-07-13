package com.example.paymentgateway.payment.gateway.dto;

import com.example.paymentgateway.common.entity.Money;
import com.example.paymentgateway.common.enums.PaymentMethod;
import lombok.Builder;

import java.util.Map;
import java.util.UUID;

@Builder
public record PaymentRequest(
		UUID paymentId,
		
		UUID orderId,
		
		UUID merchantId,
		
		Money amount,
		
		PaymentMethod method,
		
		Map<String, Object> methodDetails
) {

}
