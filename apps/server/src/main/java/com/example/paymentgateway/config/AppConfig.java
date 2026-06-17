package com.example.paymentgateway.config;

import com.example.paymentgateway.merchant.mapper.ApiKeyMapper;
import com.example.paymentgateway.merchant.mapper.MerchantMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	
	@Bean
	public MerchantMapper merchantMapper() {
		return Mappers.getMapper(MerchantMapper.class);
	}
	
	@Bean
	public ApiKeyMapper apiKeyMapper() {
		return Mappers.getMapper(ApiKeyMapper.class);
	}
}
