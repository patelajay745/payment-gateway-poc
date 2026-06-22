package com.example.paymentgateway.payment.service.impl;

import com.example.paymentgateway.common.exception.DuplicateResourceException;
import com.example.paymentgateway.common.security.MerchantPrincipal;
import com.example.paymentgateway.payment.dto.request.CreateOrderRequest;
import com.example.paymentgateway.payment.dto.response.CreateOrderResponse;
import com.example.paymentgateway.payment.entity.OrderRecord;
import com.example.paymentgateway.payment.mapper.CreateOrderMapper;
import com.example.paymentgateway.payment.repository.OrderRepository;
import com.example.paymentgateway.payment.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
	
	private final OrderRepository orderRepository;
	
	private final CreateOrderMapper createOrderMapper;
	
	@Value("${payment.order.default-order-expiry-minutes:30}")
	private int defaultOrderExpiryMinutes;
	
	@Override
	public CreateOrderResponse createOrder(CreateOrderRequest request) {
		
		MerchantPrincipal principal =
				(MerchantPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UUID merchantId = principal.getMerchant().getId();
		
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
		
		return createOrderMapper.toResponse(order);
	}
	
	private void checkMerchantIdAndReceipt(UUID merchantId, String receipt) {
		
		if (receipt != null && orderRepository.existsByMerchantIdAndReceipt(merchantId, receipt)) {
			throw new DuplicateResourceException("Order_Receipt_Duplicate", "Order with receipt already exists");
		}
	}
}
