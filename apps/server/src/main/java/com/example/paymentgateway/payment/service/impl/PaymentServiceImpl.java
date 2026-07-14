package com.example.paymentgateway.payment.service.impl;

import com.example.paymentgateway.common.enums.OrderStatus;
import com.example.paymentgateway.common.enums.PaymentEvent;
import com.example.paymentgateway.common.enums.PaymentStatus;
import com.example.paymentgateway.common.exception.BusinessRuleViolationException;
import com.example.paymentgateway.common.exception.ResourceNotFoundException;
import com.example.paymentgateway.common.utils.Utils;
import com.example.paymentgateway.merchant.entity.Merchant;
import com.example.paymentgateway.payment.dto.request.PaymentInitRequest;
import com.example.paymentgateway.payment.dto.response.PaymentResponse;
import com.example.paymentgateway.payment.entity.OrderRecord;
import com.example.paymentgateway.payment.entity.Payment;
import com.example.paymentgateway.payment.gateway.PaymentGatewayRouter;
import com.example.paymentgateway.payment.gateway.dto.PaymentRequest;
import com.example.paymentgateway.payment.gateway.dto.PaymentResult;
import com.example.paymentgateway.payment.mapper.PaymentMapper;
import com.example.paymentgateway.payment.repository.OrderRepository;
import com.example.paymentgateway.payment.repository.PaymentRepository;
import com.example.paymentgateway.payment.service.PaymentService;
import com.example.paymentgateway.payment.statemachine.PaymentTransitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
	
	private final OrderRepository orderRepository;
	
	private final PaymentRepository paymentRepository;
	
	private final PaymentGatewayRouter paymentGatewayRouter;
	
	private final PaymentMapper paymentMapper;
	
	private final PaymentTransitionService paymentTransitionService;
	
	@Override
	@Transactional
	public PaymentResponse initiate(PaymentInitRequest request) {
		
		Merchant merchant = Utils.getCurrentMerchant();
		
		OrderRecord order = orderRepository.findByIdAndMerchantId(request.orderId(), merchant.getId())
				                    .orElseThrow(
						                    () -> new ResourceNotFoundException("Order",
								                    request.orderId().toString()));
		if (order.getOrderStatus() != OrderStatus.CREATED && order.getOrderStatus() != OrderStatus.ATTEMPTED) {
			throw new BusinessRuleViolationException("ORDER_NOT_PAYABLE", "Order cannot accept payment in status:" +
					                                                              order.getOrderStatus());
		}
		
		order.updateOrderStatus(OrderStatus.ATTEMPTED);
		order.setAttempts(order.getAttempts() + 1);
		
		Payment payment = Payment
				                  .builder()
				                  .order(order)
				                  .merchantId(merchant.getId())
				                  .amount(order.getAmount())
				                  .status(PaymentStatus.CREATED)
				                  .method(request.method())
				                  .methodDetails(request.methodDetails())
				                  .build();
		
		
		payment = paymentRepository.save(payment);
		
		PaymentRequest paymentRequest = PaymentRequest
				                                .builder()
				                                .paymentId(payment.getId())
				                                .orderId(request.orderId())
				                                .merchantId(merchant.getId())
				                                .amount(order.getAmount())
				                                .method(request.method())
				                                .methodDetails(request.methodDetails())
				                                .build();
		
		
		PaymentResult result = paymentGatewayRouter.initiate(paymentRequest);
		
		
		switch (result) {
			case PaymentResult.Pending pending -> payment.setPaymentProcessorReference(pending.registrationRef());
			case PaymentResult.Failure failer -> {
//				payment.setStatus(PaymentStatus.FAILED);
				paymentTransitionService.apply(payment, PaymentEvent.AUTHORIZED_FAIL);
				payment.setErrorCode(failer.errorCode());
				payment.setErrorDescription(failer.errorDescription());
			}
			case PaymentResult.Success success -> {
			
			}
		}
		
		payment = paymentRepository.save(payment);
		orderRepository.save(order);
		
		
		return paymentMapper.toResponse(payment);
	}
	
	@Override
	@Transactional
	public PaymentResponse capture(UUID paymentId) {
		Merchant merchant = Utils.getCurrentMerchant();
		Payment payment =
				paymentRepository.findByIDAndMerchantId(paymentId, merchant.getId())
						.orElseThrow(() -> new ResourceNotFoundException("Payment", paymentId.toString()));
		
		paymentTransitionService.apply(payment, PaymentEvent.CAPTURED_REQUEST);
		
		PaymentResult paymentResult =
				paymentGatewayRouter.capture(payment.getMethod(), paymentId);
		
		if (paymentResult instanceof PaymentResult.Success success) {
			paymentTransitionService.apply(payment, PaymentEvent.CAPTURED_SUCCESS);
			payment.setCapturedAt(Instant.now());
			log.info("Payment captured , paymentId : {}", paymentId);
		} else if (paymentResult instanceof PaymentResult.Failure failure) {
			paymentTransitionService.apply(payment, PaymentEvent.CAPTURED_FAIL);
			payment.setErrorCode(failure.errorCode());
			payment.setErrorDescription(failure.errorDescription());
			log.warn("Payment capture failed, paymentId : {}", paymentId);
		}
		
		payment = paymentRepository.save(payment);
		
		return paymentMapper.toResponse(payment);
	}
}
