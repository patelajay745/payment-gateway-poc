package com.example.paymentgateway.payment.controller;

import com.example.paymentgateway.payment.dto.request.PaymentInitRequest;
import com.example.paymentgateway.payment.dto.response.PaymentResponse;
import com.example.paymentgateway.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
	
	private final PaymentService paymentService;
	
	@PostMapping
	public ResponseEntity<PaymentResponse> initiatePayment(@RequestBody @Valid PaymentInitRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.initiate(request));
	}
}
