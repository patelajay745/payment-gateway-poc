package com.example.paymentgateway.payment.gateway.adapter;

import com.example.paymentgateway.payment.gateway.PaymentAdapter;
import com.example.paymentgateway.payment.gateway.dto.PaymentRequest;
import com.example.paymentgateway.payment.gateway.dto.PaymentResult;

import java.util.UUID;

public class CardPaymentAdapter implements PaymentAdapter {
	
	@Override
	public PaymentResult initiate(PaymentRequest request) {
		
		return null;
	}
	
	@Override
	public PaymentResult capture(UUID paymentId) {
		return null;
	}
}
