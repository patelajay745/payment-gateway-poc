package com.example.paymentgateway.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateResources(DuplicateResourceException e) {
		return ResponseEntity
				       .status(HttpStatus.CONFLICT)
				       .body(ErrorResponse.of(e.getErrorCode(), e.getMessage()));
	}
}
