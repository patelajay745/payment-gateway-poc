package com.example.paymentgateway.common.mapper;

public interface BaseMapper<ENTITY, REQUEST, RESPONSE> {
	
	RESPONSE toResponse(ENTITY entity);
	
	ENTITY toEntity(REQUEST request);
}
