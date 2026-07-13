package com.example.paymentgateway.payment.gateway;

import com.example.paymentgateway.common.enums.PaymentMethod;
import com.example.paymentgateway.payment.gateway.dto.PaymentRequest;
import com.example.paymentgateway.payment.gateway.dto.PaymentResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PaymentGatewayRouter {
	
	private final Map<PaymentMethod, PaymentAdapter> paymentAdapters;
	
	public PaymentResult initiate(PaymentRequest request) {
		PaymentAdapter adapter = paymentAdapters.get(request.method());
		
		if (adapter == null) {
			throw new IllegalArgumentException("No Payment adapter registered for method : " + request.method());
		}
		
		return adapter.initiate(request);
	}
}
