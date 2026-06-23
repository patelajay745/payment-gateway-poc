package com.example.paymentgateway.merchant.mapper;

import com.example.paymentgateway.merchant.dto.response.CreateApiKeyResponse;
import com.example.paymentgateway.merchant.dto.response.GetAllApiKeyResponse;
import com.example.paymentgateway.merchant.entity.ApiKey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApiKeyMapper {
	
	@Mapping(target = "keySecret", source = "rawSecret")
	CreateApiKeyResponse toResponse(ApiKey apiKey, String rawSecret);
	
	GetAllApiKeyResponse toGetAllResponse(ApiKey apiKey);
	
	List<GetAllApiKeyResponse> toGetAllResponse(List<ApiKey> apiKeys);
}
