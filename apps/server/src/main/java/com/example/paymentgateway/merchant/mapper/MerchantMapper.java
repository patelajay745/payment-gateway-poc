package com.example.paymentgateway.merchant.mapper;

import com.example.paymentgateway.merchant.dto.response.MerchantSignupResponse;
import com.example.paymentgateway.merchant.entity.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MerchantMapper {

//	@Mapping(target = "id", ignore = true)
//	@Mapping(target = "status", ignore = true)
//	@Mapping(target = "createdAt", ignore = true)
//	@Mapping(target = "updatedAt", ignore = true)
//	@Mapping(target = "createdBy", ignore = true)
//	@Mapping(target = "businessType", expression = "java(com.example.paymentgateway.common.enums.BusinessType" +
//			                                               ".valueOf" +
//			                                               "(request.businessType()))")
//	Merchant toEntity(MerchantSignupRequest request);
	
	@Mapping(source = "status", target = "merchantStatus")
	MerchantSignupResponse toResponse(Merchant entity);
}
