package com.example.paymentgateway.vault.dto.response;

import com.example.paymentgateway.common.enums.CardBrand;

public record TokenizeResponse(
		String token,
		
		String lastFour,
		
		CardBrand brand,
		
		Integer expiryMonth,
		
		Integer expiryYear
) {

}
