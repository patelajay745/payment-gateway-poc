package com.example.paymentgateway.merchant.mapper;

import com.example.paymentgateway.common.mapper.BaseMapper;
import com.example.paymentgateway.merchant.dto.request.MerchantSignupRequest;
import com.example.paymentgateway.merchant.dto.response.MerchantSignupResponse;
import com.example.paymentgateway.merchant.entity.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MerchantMapper extends BaseMapper<Merchant, MerchantSignupRequest, MerchantSignupResponse> {
	
	@Override
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "status", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "businessType", expression = "java(com.example.paymentgateway.common.enums.BusinessType" +
			                                               ".valueOf" +
			                                               "(request.businessType()))")
	Merchant toEntity(MerchantSignupRequest request);
	
	@Override
	MerchantSignupResponse toResponse(Merchant entity);
}
