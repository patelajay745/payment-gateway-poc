package com.example.paymentgateway.payment.gateway.adapter;

import com.example.paymentgateway.common.enums.PaymentMethod;
import com.example.paymentgateway.payment.gateway.PaymentAdapter;
import com.example.paymentgateway.payment.gateway.dto.PaymentRequest;
import com.example.paymentgateway.payment.gateway.dto.PaymentResult;
import com.example.paymentgateway.payment.processor.PaymentProcessorRouter;
import com.example.paymentgateway.payment.processor.dto.PaymentProcessorRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NetBankingAdapter implements PaymentAdapter {
	
	private final PaymentProcessorRouter paymentProcessorRouter;
	
	@Override
	public PaymentResult initiate(PaymentRequest request) {
		log.info("Initiate Payment with NetBankingAdapter, paymentId : {}", request.paymentId());
		
		PaymentProcessorRequest paymentProcessorRequest = PaymentProcessorRequest.nonCard(
				request.paymentId(),
				PaymentMethod.NETBANKING,
				request.amount(),
				request.methodDetails()
		);
		return null;
	}
}
