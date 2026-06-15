package com.example.paymentgateway.merchant.dto.response;

import com.example.paymentgateway.common.enums.BusinessType;
import com.example.paymentgateway.common.enums.MerchantStatus;

import java.util.UUID;

public record MerchantSignupResponse(
		
		UUID id,
		
		String name,
		
		String email,
		
		String businessName,
		
		BusinessType businessType,
		
		MerchantStatus merchantStatus

) {

}
