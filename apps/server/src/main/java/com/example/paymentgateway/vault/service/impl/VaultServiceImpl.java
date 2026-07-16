package com.example.paymentgateway.vault.service.impl;

import com.example.paymentgateway.common.enums.CardBrand;
import com.example.paymentgateway.common.utils.Utils;
import com.example.paymentgateway.merchant.entity.Merchant;
import com.example.paymentgateway.vault.dto.request.TokenizeRequest;
import com.example.paymentgateway.vault.dto.response.TokenizeResponse;
import com.example.paymentgateway.vault.repository.CardTokenRepository;
import com.example.paymentgateway.vault.repository.VaultCardRepository;
import com.example.paymentgateway.vault.service.VaultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class VaultServiceImpl implements VaultService {
	
	private final CardTokenRepository cardTokenRepository;
	
	private final VaultCardRepository vaultCardRepository;
	
	@Override
	public TokenizeResponse tokenize(TokenizeRequest request) {
		Merchant merchant = Utils.getCurrentMerchant();
		
		String lastFour = request.pan().substring(request.pan().length() - 4);
		String bin = request.pan().substring(0, 6);
		CardBrand cardBrand = detectBrand(request.pan());
		
		return null;
	}
	
	private CardBrand detectBrand(
			String pan) {
		if (pan.startsWith("4")) return CardBrand.VISA;
		if (pan.startsWith("5") || pan.startsWith("2")) return CardBrand.MASTERCARD;
		if (pan.startsWith("37") || pan.startsWith("34")) return CardBrand.AMEX;
		
		return CardBrand.RUPAY;
	}
}
