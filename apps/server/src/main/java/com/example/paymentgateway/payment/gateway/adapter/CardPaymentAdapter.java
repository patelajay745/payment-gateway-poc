package com.example.paymentgateway.payment.gateway.adapter;

import com.example.paymentgateway.payment.gateway.PaymentAdapter;
import com.example.paymentgateway.payment.gateway.dto.PaymentRequest;
import com.example.paymentgateway.payment.gateway.dto.PaymentResult;
import com.example.paymentgateway.payment.processor.dto.PaymentProcessorResponse;
import com.example.paymentgateway.vault.service.VaultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class CardPaymentAdapter implements PaymentAdapter {
	
	private final VaultService vaultService;
	
	@Override
	public PaymentResult initiate(PaymentRequest request) {
		
		String token = (String) request.methodDetails().get("token");
		
		PaymentProcessorResponse paymentProcessorResponse =
				vaultService.charge(request.paymentId(), token, request.amount(), request.methodDetails());
		
		return switch (paymentProcessorResponse) {
			case PaymentProcessorResponse.Success success -> new PaymentResult.Success(success.bankRef());
			case PaymentProcessorResponse.Failure failure -> new PaymentResult.Failure(failure.errorCode(),
					failure.errorDescription());
			case PaymentProcessorResponse.Pending pending -> new PaymentResult.Pending(pending.processorRef());
		};
	}
	
	@Override
	public PaymentResult capture(UUID paymentId) {
		return null;
	}
}
