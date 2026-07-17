package com.example.paymentgateway.payment.processor.strategy;

import com.example.paymentgateway.common.utils.RandomizerUtil;
import com.example.paymentgateway.payment.processor.PaymentProcessor;
import com.example.paymentgateway.payment.processor.dto.PaymentProcessorRequest;
import com.example.paymentgateway.payment.processor.dto.PaymentProcessorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CardPaymentProcessor implements PaymentProcessor {
	
	public static final String PAN_CARD_DECLINED = "400000000000002";
	
	public static final String PAN_CARD_EXPIRED = "400000000000069";
	
	@Override
	public PaymentProcessorResponse charge(PaymentProcessorRequest request) {
		
		String pan = request.pan();
		
		
		if (PAN_CARD_DECLINED.equals(pan)) {
			log.warn("Card declined");
			return new PaymentProcessorResponse.Failure("CARD_DECLINED", "Card declined by bank");
		}
		
		if (PAN_CARD_EXPIRED.equals(pan)) {
			log.warn("Card expired");
			return new PaymentProcessorResponse.Failure("CARD_EXPIRED", "Card expired ");
		}
		
		String processorRef = "CARD_PROCESSOR_" + RandomizerUtil.randomBase64(16);
		
		return new PaymentProcessorResponse.Pending(processorRef);
	}
}
