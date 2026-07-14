package com.example.paymentgateway.payment.gateway.adapter;

import com.example.paymentgateway.common.enums.PaymentMethod;
import com.example.paymentgateway.payment.gateway.PaymentAdapter;
import com.example.paymentgateway.payment.gateway.dto.PaymentRequest;
import com.example.paymentgateway.payment.gateway.dto.PaymentResult;
import com.example.paymentgateway.payment.processor.PaymentProcessorRouter;
import com.example.paymentgateway.payment.processor.dto.PaymentProcessorRequest;
import com.example.paymentgateway.payment.processor.dto.PaymentProcessorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class NetBankingAdapter implements PaymentAdapter {
	
	private final PaymentProcessorRouter paymentProcessorRouter;
	
	@Override
	public PaymentResult initiate(PaymentRequest request) {
		log.info("Initiate Payment with NetBankingAdapter, paymentId : {}", request.paymentId());
		
		try {
			PaymentProcessorRequest paymentProcessorRequest = PaymentProcessorRequest.nonCard(
					request.paymentId(),
					PaymentMethod.NETBANKING,
					request.amount(),
					request.methodDetails()
			);
			
			PaymentProcessorResponse response = paymentProcessorRouter.charge(paymentProcessorRequest);
			
			
			return switch (response) {
				case PaymentProcessorResponse.Failure failure -> new PaymentResult.Failure(failure.errorCode(),
						failure.errorDescription());
				
				case PaymentProcessorResponse.Pending pending -> new PaymentResult.Pending(pending.processorRef());
				
				case PaymentProcessorResponse.Success success -> new PaymentResult.Success(success.bankRef());
			};
		} catch (Exception e) {
			log.warn("NetBanking failed, paymentId : {}", request.paymentId());
			return new PaymentResult.Failure("NBK_FAILED", e.getMessage());
		}
	}
	
	@Override
	public PaymentResult capture(UUID paymentId) {
		return new PaymentResult.Success("NBK_REF");
	}
}
