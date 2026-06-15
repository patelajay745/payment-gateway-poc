package com.example.paymentgateway.merchant.controller;

import com.example.paymentgateway.merchant.dto.request.MerchantSignupRequest;
import com.example.paymentgateway.merchant.dto.response.MerchantSignupResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/auth")
public class AuthController {
	
	@PostMapping("/signup")
	public ResponseEntity<MerchantSignupResponse> registerUser(@Valid MerchantSignupRequest request) {
	
	}
}
