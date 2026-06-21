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

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/v1/orders")
@RestController
public class OrderController {
	
	private final OrderService orderService;
	
	UUID merchantId = UUID.fromString("cc2e1e71-ac5d-4ba4-8496-2e9c68145217");
	
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
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(merchantId, request));
	}
}
