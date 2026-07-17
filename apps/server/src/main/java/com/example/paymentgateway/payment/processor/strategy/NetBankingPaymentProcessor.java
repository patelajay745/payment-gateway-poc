package com.example.paymentgateway.payment.processor.strategy;

import com.example.paymentgateway.common.utils.RandomizerUtil;
import com.example.paymentgateway.payment.processor.PaymentProcessor;
import com.example.paymentgateway.payment.processor.dto.PaymentProcessorRequest;
import com.example.paymentgateway.payment.processor.dto.PaymentProcessorResponse;

public class NetBankingPaymentProcessor implements PaymentProcessor {
	
	@Override
	public PaymentProcessorResponse charge(PaymentProcessorRequest request) {
		
		final String BANK_CODE_FAIL = "BANK_CODE_FAIL";
		
		String bankCode = request.methodDetails() != null ? request.methodDetails().get("BANK").toString() : null;
		
		if (BANK_CODE_FAIL.equals(bankCode)) {
			return new PaymentProcessorResponse.Failure("BANK_REJECTED",
					"Banked rejected the transaction registration");
		}
		
		String processorRef = "NBK_PROCESSOR_" + RandomizerUtil.randomBase64(16);
		
		//String redirectRef = "http://REDIRECT_BANK.com/" + processorRef;
		
		
		return new PaymentProcessorResponse.Pending(processorRef);
	}
}
