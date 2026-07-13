package com.example.paymentgateway.common.utils;

import com.example.paymentgateway.common.security.MerchantPrincipal;
import com.example.paymentgateway.merchant.entity.Merchant;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {
	
	public static Merchant getCurrentMerchant() {
		MerchantPrincipal principal =
				(MerchantPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		assert principal != null;
		return principal.getMerchant();
	}
}
