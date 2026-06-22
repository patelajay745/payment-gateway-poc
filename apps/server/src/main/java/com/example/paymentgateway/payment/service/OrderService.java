package com.example.paymentgateway.payment.service;

import com.example.paymentgateway.payment.dto.request.CreateOrderRequest;
import com.example.paymentgateway.payment.dto.response.CreateOrderResponse;

public interface OrderService {
	
	CreateOrderResponse createOrder(CreateOrderRequest request);
}
