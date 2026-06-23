package com.example.paymentgateway.payment.controller;

import com.example.paymentgateway.payment.dto.request.CreateOrderRequest;
import com.example.paymentgateway.payment.dto.response.OrderResponse;
import com.example.paymentgateway.payment.dto.response.PaymentResponse;
import com.example.paymentgateway.payment.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/v1/orders")
@RestController
public class OrderController {
	
	private final OrderService orderService;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderResponse> createOrderUsingJsonData(
			@RequestBody @Valid CreateOrderRequest request) {
		return createOrder(request);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<OrderResponse> createOrderUsingFormData(
			@ModelAttribute @Valid CreateOrderRequest request) {
		return createOrder(request);
	}
	
	@PostMapping("/{orderId}")
	public ResponseEntity<OrderResponse> getOrderById(@PathVariable UUID orderId) {
		return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderById(orderId));
	}
	
	@PostMapping("/{orderId}/cancel")
	public ResponseEntity<?> cancelOrder(@PathVariable UUID orderId) {
		orderService.cancelOrder(orderId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PostMapping("/{orderId}/payments")
	public ResponseEntity<List<PaymentResponse>> listPayments(@PathVariable UUID orderId) {
		return ResponseEntity.status(HttpStatus.OK).body(orderService.listPayments(orderId));
	}
	
	private ResponseEntity<OrderResponse> createOrder(CreateOrderRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request));
	}
}
