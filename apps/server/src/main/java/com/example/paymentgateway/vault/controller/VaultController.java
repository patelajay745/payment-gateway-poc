package com.example.paymentgateway.vault.controller;

import com.example.paymentgateway.vault.dto.request.TokenizeRequest;
import com.example.paymentgateway.vault.dto.response.TokenizeResponse;
import com.example.paymentgateway.vault.service.VaultService;
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
@RequestMapping("/v1/vault")
public class VaultController {
	
	private final VaultService vaultService;
	
	@PostMapping("/tokenize")
	public ResponseEntity<TokenizeResponse> tokenizeCard(@RequestBody @Valid TokenizeRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(vaultService.tokenize(request));
	}
}
