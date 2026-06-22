package com.example.paymentgateway.payment.controller;

import com.example.paymentgateway.payment.dto.request.CreateOrderRequest;
import com.example.paymentgateway.payment.dto.response.CreateOrderResponse;
import com.example.paymentgateway.payment.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/v1/orders")
@RestController
public class OrderController {
	
	private final OrderService orderService;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CreateOrderResponse> createOrderUsingJsonData(
			@RequestBody @Valid CreateOrderRequest request) {
		return createOrder(request);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<CreateOrderResponse> createOrderUsingFormData(
			@ModelAttribute @Valid CreateOrderRequest request) {
		return createOrder(request);
	}
	
	private ResponseEntity<CreateOrderResponse> createOrder(CreateOrderRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request));
	}
}
