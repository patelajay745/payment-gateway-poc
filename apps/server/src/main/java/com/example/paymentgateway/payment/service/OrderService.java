package com.example.paymentgateway.payment.service;

import com.example.paymentgateway.payment.dto.request.CreateOrderRequest;
import com.example.paymentgateway.payment.dto.response.CreateOrderResponse;

import java.util.UUID;

public interface OrderService {
	
	CreateOrderResponse createOrder(UUID merchantId, CreateOrderRequest request);
}
