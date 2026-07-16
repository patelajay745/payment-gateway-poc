package com.example.paymentgateway.vault.service.impl;

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
		
		
		return null;
	}
}
