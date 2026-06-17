package com.example.paymentgateway.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ApiResponse<T>(
		boolean success,
		
		T data,
		
		LocalDateTime timeStamp
) {
	
	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(true, data, LocalDateTime.now());
	}
}
