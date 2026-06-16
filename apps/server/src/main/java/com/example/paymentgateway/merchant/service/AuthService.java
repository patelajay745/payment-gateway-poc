package com.example.paymentgateway.merchant.service;

import com.example.paymentgateway.merchant.dto.request.MerchantSignupRequest;
import com.example.paymentgateway.merchant.dto.response.MerchantSignupResponse;
import jakarta.validation.Valid;

public interface AuthService {
	
	MerchantSignupResponse registerMerchant(@Valid MerchantSignupRequest request);
}
