package com.example.paymentgateway.merchant.service;

import com.example.paymentgateway.merchant.dto.request.MerchantLoginRequest;
import com.example.paymentgateway.merchant.dto.request.MerchantSignupRequest;
import com.example.paymentgateway.merchant.dto.response.MerchantLoginResponse;
import com.example.paymentgateway.merchant.dto.response.MerchantSignupResponse;

public interface AuthService {
	
	MerchantSignupResponse registerMerchant(MerchantSignupRequest request);
	
	MerchantLoginResponse loginMerchant(MerchantLoginRequest request);
}
