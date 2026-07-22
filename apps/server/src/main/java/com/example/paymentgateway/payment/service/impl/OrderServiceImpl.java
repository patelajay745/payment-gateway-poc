package com.example.paymentgateway.payment.service.impl;

import com.example.paymentgateway.common.enums.OrderStatus;
import com.example.paymentgateway.common.exception.BusinessRuleViolationException;
import com.example.paymentgateway.common.exception.DuplicateResourceException;
import com.example.paymentgateway.common.exception.ResourceNotFoundException;
import com.example.paymentgateway.common.utils.Utils;
import com.example.paymentgateway.payment.dto.request.CreateOrderRequest;
import com.example.paymentgateway.payment.dto.response.OrderResponse;
import com.example.paymentgateway.payment.dto.response.PaymentResponse;
import com.example.paymentgateway.payment.entity.OrderRecord;
import com.example.paymentgateway.payment.entity.Payment;
import com.example.paymentgateway.payment.mapper.OrderMapper;
import com.example.paymentgateway.payment.repository.OrderRepository;
import com.example.paymentgateway.payment.repository.PaymentRepository;
import com.example.paymentgateway.payment.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
	
	private final OrderRepository orderRepository;
	
	private final OrderMapper orderMapper;
	
	private final PaymentRepository paymentRepository;
	
	@Value("${payment.order.default-order-expiry-minutes:30}")
	private int defaultOrderExpiryMinutes;
	
	public OrderRecord getOrder(UUID orderId) {
		UUID merchantId = Utils.getCurrentMerchant().getId();
		
		return orderRepository.findByIdAndMerchantId(orderId, merchantId)
				       .orElseThrow(
						       () -> new ResourceNotFoundException("ORDER",
								       "Order with " + orderId + " not found"));
	}
	
	@Override
	public OrderResponse createOrder(CreateOrderRequest request) {


//		UUID merchantId = Utils.getCurrentMerchant().getId();
		UUID merchantId = UUID.fromString("e51aedc1-95e7-443b-953b-168054c85058");
		
		
		checkMerchantIdAndReceipt(merchantId, request.receipt());
		
		OrderRecord orderTobeSaved = OrderRecord
				                             .builder()
				                             .receipt(request.receipt())
				                             .amount(request.amount())
				                             .notes(request.notes())
				                             .merchantId(merchantId)
				                             .expires(request.expiresAt() != null ? request.expiresAt() :
						                                      LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC)
						                                      .plus(
								                                      Duration.ofMinutes(defaultOrderExpiryMinutes)))
				                             .build();
		
		OrderRecord order = orderRepository.save(orderTobeSaved);
		
		return orderMapper.toResponse(order);
	}
	
	@Override
	public OrderResponse getOrderById(UUID orderId) {
		
		OrderRecord order = getOrder(orderId);
		
		return orderMapper.toResponse(order);
	}
	
	@Override
	@Transactional
	public void cancelOrder(UUID orderId) {
		
		OrderRecord order = getOrder(orderId);
		
		if (order.getOrderStatus() == OrderStatus.CANCELLED || order.getOrderStatus() == OrderStatus.PAID) {
			throw new BusinessRuleViolationException("ORDER_CANCEL", "Order can't be cancel now");
		}
		
		order.setOrderStatus(OrderStatus.CANCELLED);
	}
	
	@Override
	public List<PaymentResponse> listPayments(UUID orderId) {
		OrderRecord order = getOrder(orderId);
		
		List<Payment> paymentList = paymentRepository.findByOrderId(order.getId());
		return orderMapper.toPaymentResponseList(paymentList);
	}
	
	private void checkMerchantIdAndReceipt(UUID merchantId, String receipt) {
		
		if (receipt != null && orderRepository.existsByMerchantIdAndReceipt(merchantId, receipt)) {
			throw new DuplicateResourceException("Order_Receipt_Duplicate", "Order with receipt already exists");
		}
	}
}
