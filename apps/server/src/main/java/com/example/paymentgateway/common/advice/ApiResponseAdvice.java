package com.example.paymentgateway.common.advice;

import com.example.paymentgateway.common.dto.ApiResponse;
import com.example.paymentgateway.common.exception.GlobalExceptionHandler;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {
	
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		
		String path = ((ServletRequestAttributes) RequestContextHolder
				                                          .getRequestAttributes()).getRequest().getRequestURI();
		
		return !returnType.getParameterType().equals(ApiResponse.class)
				       && !returnType.getDeclaringClass().equals(GlobalExceptionHandler.class)
				       && !path.startsWith("/error")
				       && !path.startsWith("/actuator")
				       && !path.startsWith("/swagger")
				       && !path.startsWith("/v3/api-docs");
	}
	
	@Override
	public @Nullable Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType,
	                                        MediaType selectedContentType,
	                                        Class<? extends HttpMessageConverter<?>> selectedConverterType,
	                                        ServerHttpRequest request, ServerHttpResponse response) {
		
		
		return ApiResponse.success(body);
	}
}
