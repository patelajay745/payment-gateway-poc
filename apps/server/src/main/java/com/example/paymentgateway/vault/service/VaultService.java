package com.example.paymentgateway.vault.service;

import com.example.paymentgateway.vault.dto.request.TokenizeRequest;
import com.example.paymentgateway.vault.dto.response.TokenizeResponse;

public interface VaultService {
	
	TokenizeResponse tokenize(TokenizeRequest request);
}
