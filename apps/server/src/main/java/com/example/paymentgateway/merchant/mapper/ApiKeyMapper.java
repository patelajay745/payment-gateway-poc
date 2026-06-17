package com.example.paymentgateway.merchant.mapper;

import com.example.paymentgateway.common.mapper.BaseMapper;
import com.example.paymentgateway.merchant.dto.request.CreateApiKeyRequest;
import com.example.paymentgateway.merchant.dto.response.CreateApiKeyResponse;
import com.example.paymentgateway.merchant.entity.ApiKey;
import org.mapstruct.Mapper;

@Mapper
public interface ApiKeyMapper extends BaseMapper<ApiKey, CreateApiKeyRequest, CreateApiKeyResponse> {
	
	CreateApiKeyResponse toResponse(ApiKey apiKey, String rawSecret);
}
