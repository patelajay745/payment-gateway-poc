package com.example.paymentgateway.payment.service;

import com.example.paymentgateway.payment.dto.request.CreateOrderRequest;
import com.example.paymentgateway.payment.dto.response.OrderResponse;
import com.example.paymentgateway.payment.dto.response.PaymentResponse;

import java.util.UUID;

public interface OrderService {
	
	OrderResponse createOrder(CreateOrderRequest request);
	
	OrderResponse getOrderById(UUID orderId);
	
	void cancelOrder(UUID orderId);
	
	PaymentResponse listPayments(UUID orderId);
}
