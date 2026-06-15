package com.example.paymentgateway.merchant.service.impl;

import com.example.paymentgateway.merchant.repository.MerchantRepository;
import com.example.paymentgateway.merchant.service.MerchantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MerchantServiceImpl implements MerchantService {
	
	private final MerchantRepository repository;
}
