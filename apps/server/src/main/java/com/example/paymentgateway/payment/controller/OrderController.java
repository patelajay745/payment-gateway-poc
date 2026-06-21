package com.example.paymentgateway.payment.controller;

import com.example.paymentgateway.payment.dto.request.CreateOrderRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/v1/orders")
@RestController
public class OrderController {
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid CreateOrderRequest request) {
	
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<OrderResponse> createOrder(@ModelAttribute @Valid CreateOrderRequest request) {
	
	}
}
