package com.example.paymentgateway.payment.processor;

import com.example.paymentgateway.common.enums.PaymentMethod;
import com.example.paymentgateway.payment.processor.dto.PaymentProcessorRequest;
import com.example.paymentgateway.payment.processor.dto.PaymentProcessorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class PaymentProcessorRouter {
	
	private final Map<PaymentMethod, PaymentProcessor> PaymentProcessorMap;
	
	public PaymentProcessorResponse charge(PaymentProcessorRequest request) {
		PaymentProcessor processor = PaymentProcessorMap.get(request.method());
		
		if (processor == null) {
			throw new IllegalArgumentException("No payment processor registered for method : " + request.method());
		}
		
		return processor.charge(request);
	}
}
