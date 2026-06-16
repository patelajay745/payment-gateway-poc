package com.example.paymentgateway.merchant.service.impl;

import com.example.paymentgateway.common.enums.UserRole;
import com.example.paymentgateway.merchant.dto.request.MerchantSignupRequest;
import com.example.paymentgateway.merchant.dto.response.MerchantSignupResponse;
import com.example.paymentgateway.merchant.entity.AppUsers;
import com.example.paymentgateway.merchant.entity.Merchant;
import com.example.paymentgateway.merchant.mapper.MerchantMapper;
import com.example.paymentgateway.merchant.repository.AppUserRepository;
import com.example.paymentgateway.merchant.repository.MerchantRepository;
import com.example.paymentgateway.merchant.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
	
	private final AppUserRepository appuserRepository;
	
	private final MerchantRepository merchantRepository;
	
	private final MerchantMapper merchantMapper;
	
	@Override
	public MerchantSignupResponse registerMerchant(MerchantSignupRequest request) {
		
		if (merchantRepository.existsByEmail(request.email())) {
			throw new RuntimeException("Merchant with email already exists : " + request.email());
		}
		
		
		Merchant savedMerchant = merchantRepository.save(merchantMapper.toEntity(request));
		
		AppUsers appUser = AppUsers
				                   .builder()
				                   .email(savedMerchant.getEmail())
				                   .merchant(savedMerchant)
				                   .passwordHashed(request.password())
				                   .role(UserRole.OWNER)
				                   .build();
		
		appuserRepository.save(appUser);
		
		return merchantMapper.toResponse(savedMerchant);
	}
}
