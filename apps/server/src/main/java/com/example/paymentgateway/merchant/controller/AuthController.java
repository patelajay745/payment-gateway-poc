package com.example.paymentgateway.merchant.controller;

import com.example.paymentgateway.merchant.dto.request.MerchantLoginRequest;
import com.example.paymentgateway.merchant.dto.request.MerchantSignupRequest;
import com.example.paymentgateway.merchant.dto.response.MerchantLoginResponse;
import com.example.paymentgateway.merchant.dto.response.MerchantSignupResponse;
import com.example.paymentgateway.merchant.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping("/")
	public ResponseEntity<MerchantSignupResponse> registerUser(@RequestBody @Valid MerchantSignupRequest request) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerMerchant(request));
	}
	
	@PostMapping("/login")
	public ResponseEntity<MerchantLoginResponse> loginUser(@RequestBody @Valid MerchantLoginRequest request) {
		return ResponseEntity.status(200).body(authService.loginMerchant(request));
	}
}
