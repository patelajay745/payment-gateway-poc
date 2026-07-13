package com.example.paymentgateway.payment.processor;

import com.example.paymentgateway.payment.processor.dto.PaymentProcessorRequest;
import com.example.paymentgateway.payment.processor.dto.PaymentProcessorResponse;

public interface PaymentProcessor {
	
	PaymentProcessorResponse charge(PaymentProcessorRequest request);
}
