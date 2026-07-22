package com.example.paymentgateway.merchant.service.impl;

import com.example.paymentgateway.common.enums.BusinessType;
import com.example.paymentgateway.common.enums.UserRole;
import com.example.paymentgateway.common.exception.DuplicateResourceException;
import com.example.paymentgateway.common.exception.ResourceNotFoundException;
import com.example.paymentgateway.common.utils.JWTUtils;
import com.example.paymentgateway.merchant.dto.request.MerchantLoginRequest;
import com.example.paymentgateway.merchant.dto.request.MerchantSignupRequest;
import com.example.paymentgateway.merchant.dto.response.MerchantLoginResponse;
import com.example.paymentgateway.merchant.dto.response.MerchantSignupResponse;
import com.example.paymentgateway.merchant.entity.AppUsers;
import com.example.paymentgateway.merchant.entity.Merchant;
import com.example.paymentgateway.merchant.mapper.MerchantMapper;
import com.example.paymentgateway.merchant.repository.AppUserRepository;
import com.example.paymentgateway.merchant.repository.MerchantRepository;
import com.example.paymentgateway.merchant.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
	
	private final AppUserRepository appuserRepository;
	
	private final MerchantRepository merchantRepository;
	
	private final MerchantMapper merchantMapper;
	
	private final PasswordEncoder passwordEncoder;
	
	private final JWTUtils jwtUtils;
	
	private final AuthenticationManager authenticationManager;
	
	@Override
	@Transactional
	public MerchantSignupResponse registerMerchant(MerchantSignupRequest request) {
		
		if (merchantRepository.existsByEmail(request.email())) {
			throw new DuplicateResourceException("DUPLICATE_MERCHANT",
					"Merchant with email already exists: " + request.email());
		}
		
		Merchant merchantToSave = Merchant
				                          .builder()
				                          .name(request.name())
				                          .email(request.email())
				                          .businessName(request.businessName())
				                          .businessType(BusinessType.valueOf(request.businessType()))
				                          .build();
		
		Merchant savedMerchant = merchantRepository.save(merchantToSave);
		
		AppUsers appUser = AppUsers
				                   .builder()
				                   .email(savedMerchant.getEmail())
				                   .merchant(savedMerchant)
				                   .passwordHashed(passwordEncoder.encode(request.password()))
				                   .role(UserRole.OWNER)
				                   .build();
		
		appuserRepository.save(appUser);
		
		return merchantMapper.toResponse(savedMerchant);
	}
	
	@Override
	public MerchantLoginResponse loginMerchant(MerchantLoginRequest request) {
		
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.email(), request.password()));
		
		AppUsers appUsers = appuserRepository.findByEmail(request.email())
				                    .orElseThrow(() -> new ResourceNotFoundException("USER", request.email()));
		
		
		String accessToken =
				jwtUtils.generateAccessToken(appUsers.getEmail(), appUsers.getMerchant().getId(),
						appUsers.getRole().toString());
		return new MerchantLoginResponse(accessToken);
	}
}
