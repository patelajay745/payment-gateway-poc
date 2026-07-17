package com.example.paymentgateway.vault.service.impl;

import com.example.paymentgateway.common.entity.Money;
import com.example.paymentgateway.common.enums.CardBrand;
import com.example.paymentgateway.common.exception.ResourceNotFoundException;
import com.example.paymentgateway.common.utils.RandomizerUtil;
import com.example.paymentgateway.common.utils.Utils;
import com.example.paymentgateway.merchant.entity.Merchant;
import com.example.paymentgateway.payment.processor.PaymentProcessorRouter;
import com.example.paymentgateway.payment.processor.dto.PaymentProcessorRequest;
import com.example.paymentgateway.payment.processor.dto.PaymentProcessorResponse;
import com.example.paymentgateway.vault.config.VaultEncryptionConfig;
import com.example.paymentgateway.vault.dto.request.TokenizeRequest;
import com.example.paymentgateway.vault.dto.response.TokenizeResponse;
import com.example.paymentgateway.vault.entity.CardToken;
import com.example.paymentgateway.vault.entity.VaultCard;
import com.example.paymentgateway.vault.repository.CardTokenRepository;
import com.example.paymentgateway.vault.repository.VaultCardRepository;
import com.example.paymentgateway.vault.service.VaultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class VaultServiceImpl implements VaultService {
	
	private final CardTokenRepository cardTokenRepository;
	
	private final VaultCardRepository vaultCardRepository;
	
	private final BytesEncryptor bytesEncryptor;
	
	private final PaymentProcessorRouter paymentProcessorRouter;
	
	@Override
	@Transactional
	public TokenizeResponse tokenize(TokenizeRequest request) {
		Merchant merchant = Utils.getCurrentMerchant();
		
		String lastFour = request.pan().substring(request.pan().length() - 4);
		String bin = request.pan().substring(0, 6);
		CardBrand cardBrand = detectBrand(request.pan());
		
		byte[] dek = KeyGenerators.secureRandom(32).generateKey();
		byte[] encryptedPan =
				VaultEncryptionConfig.panEncrypter(dek).encrypt(request.pan().getBytes(StandardCharsets.UTF_8));
		
		byte[] encryptedDek = bytesEncryptor.encrypt(request.pan().getBytes(StandardCharsets.UTF_8));
		
		VaultCard vaultCard = VaultCard
				                      .builder()
				                      .brand(cardBrand)
				                      .expiryMonth(request.expiryMonth())
				                      .expiryYear(request.expiryYear())
				                      .bin(bin)
				                      .lastFour(lastFour)
				                      .encryptedDek(encryptedDek)
				                      .encryptedPan(encryptedPan)
				                      .cardHolderName(request.cardHolderName())
				                      .build();
		
		vaultCard = vaultCardRepository.save(vaultCard);
		
		String token = "tok_" + RandomizerUtil.randomBase64(32);
		
		cardTokenRepository.save(CardToken
				                         .builder()
				                         .token(token)
				                         .vaultCard(vaultCard)
				                         .customer(request.customerId())
				                         .merchant(merchant.getId())
				                         .build());
		
		return new TokenizeResponse(token, lastFour, cardBrand, request.expiryMonth(), request.expiryYear());
	}
	
	@Override
	public PaymentProcessorResponse charge(UUID paymentId, String token, Money amount,
	                                       Map<String, Object> methodDetails) {
		CardToken cardToken = cardTokenRepository.findByTokenAndRevokedAtIsNull(token)
				                      .orElseThrow(() -> new ResourceNotFoundException("CardToken", token));
		
		VaultCard vaultCard = cardToken.getVaultCard();
		
		byte[] panBytes = null;
		
		try {
			byte[] dek = bytesEncryptor.decrypt(vaultCard.getEncryptedDek());
			
			panBytes = VaultEncryptionConfig.panEncrypter(dek).decrypt(vaultCard.getEncryptedDek());
			
			String pan = new String(panBytes, StandardCharsets.UTF_8);
			
			String expiry = vaultCard.getExpiryMonth() + "/" + vaultCard.getExpiryYear();
			
			PaymentProcessorRequest paymentProcessorRequest =
					PaymentProcessorRequest.card(paymentId, pan, expiry, amount, methodDetails);
			
			PaymentProcessorResponse response = paymentProcessorRouter.charge(paymentProcessorRequest);
			
			log.info("Vault charge registered, token ={}****", token.substring(0, 4));
			
			
			return response;
		} catch (Exception e) {
			log.warn("Vault charge failed , token = {}****", token.substring(0, 4));
			return new PaymentProcessorResponse.Failure("VAULT_CHARGE_FAILED", e.getMessage());
		} finally {
			assert panBytes != null;
			Arrays.fill(panBytes, (byte) 0);
		}
	}
	
	private CardBrand detectBrand(
			String pan) {
		if (pan.startsWith("4")) return CardBrand.VISA;
		if (pan.startsWith("5") || pan.startsWith("2")) return CardBrand.MASTERCARD;
		if (pan.startsWith("37") || pan.startsWith("34")) return CardBrand.AMEX;
		
		return CardBrand.RUPAY;
	}
}
