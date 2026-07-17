package com.example.paymentgateway.vault.service;

import com.example.paymentgateway.common.entity.Money;
import com.example.paymentgateway.payment.processor.dto.PaymentProcessorResponse;
import com.example.paymentgateway.vault.dto.request.TokenizeRequest;
import com.example.paymentgateway.vault.dto.response.TokenizeResponse;

import java.util.Map;
import java.util.UUID;

public interface VaultService {
	
	TokenizeResponse tokenize(TokenizeRequest request);
	
	PaymentProcessorResponse charge(UUID paymentId, String token, Money amount, Map<String, Object> methodDetails);
}
