package com.example.paymentgateway.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateResources(DuplicateResourceException e) {
		return ResponseEntity
				       .status(HttpStatus.CONFLICT)
				       .body(ErrorResponse.of(e.getErrorCode(), e.getMessage()));
	}
	
	@ExceptionHandler(BusinessRuleViolationException.class)
	public ResponseEntity<ErrorResponse> handleBusinessRuleViolationException(BusinessRuleViolationException e) {
		return ResponseEntity
				       .status(HttpStatus.CONFLICT)
				       .body(ErrorResponse.of(e.getErrorCode(), e.getMessage()));
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException e) {
		
		String errorCode = e.getResourceName().toUpperCase() + "_NOT_FOUND";
		return ResponseEntity
				       .status(HttpStatus.NOT_FOUND)
				       .body(ErrorResponse.of(errorCode, e.getMessage()));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e) {
		List<ErrorResponse.FieldError> fieldErrors = e.getBindingResult()
				                                             .getFieldErrors()
				                                             .stream()
				                                             .map(fe ->
						                                                  new ErrorResponse.FieldError(fe.getField(),
								                                                  fe.getDefaultMessage()))
				                                             .toList();
		
		return ResponseEntity
				       .status(HttpStatus.BAD_REQUEST)
				       .body(ErrorResponse.of("VALIDATION_ERROR", "Request validation failed", fieldErrors));
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
		log.error("Unexpected error occurred", e);
		return ResponseEntity
				       .status(HttpStatus.INTERNAL_SERVER_ERROR)
				       .body(ErrorResponse.of("INTERNAL_SERVER_ERROR", e.getMessage()));
	}
}
