package com.example.paymentgateway.merchant.dto.request;

public record MerchantLoginRequest(
		String email,
		
		String password
) {

}
