package com.example.paymentgateway.payment.service;

import com.example.paymentgateway.payment.dto.request.PaymentInitRequest;
import com.example.paymentgateway.payment.dto.response.PaymentResponse;

public interface PaymentService {
	
	PaymentResponse initiate(PaymentInitRequest request);
}
