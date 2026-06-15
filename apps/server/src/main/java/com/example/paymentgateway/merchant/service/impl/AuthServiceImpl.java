package com.example.paymentgateway.merchant.service.impl;

import com.example.paymentgateway.merchant.repository.AppUserRepository;
import com.example.paymentgateway.merchant.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
	
	private final AppUserRepository appuserRepository;
}
