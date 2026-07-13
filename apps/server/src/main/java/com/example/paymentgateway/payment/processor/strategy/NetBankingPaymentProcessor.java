package com.example.paymentgateway.payment.processor.strategy;

import com.example.paymentgateway.payment.processor.PaymentProcessor;
import com.example.paymentgateway.payment.processor.dto.PaymentProcessorRequest;
import com.example.paymentgateway.payment.processor.dto.PaymentProcessorResponse;

public class NetBankingPaymentProcessor implements PaymentProcessor {
	
	@Override
	public PaymentProcessorResponse charge(PaymentProcessorRequest request) {
		return null;
	}
}
