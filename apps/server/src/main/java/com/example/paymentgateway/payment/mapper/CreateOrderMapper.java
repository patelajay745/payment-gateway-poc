package com.example.paymentgateway.payment.mapper;

import com.example.paymentgateway.common.mapper.BaseMapper;
import com.example.paymentgateway.payment.dto.request.CreateOrderRequest;
import com.example.paymentgateway.payment.dto.response.CreateOrderResponse;
import com.example.paymentgateway.payment.entity.OrderRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CreateOrderMapper extends BaseMapper<OrderRecord, CreateOrderRequest, CreateOrderResponse> {
	
	OrderRecord toEntity(CreateOrderRequest request);
	
	@Mapping(source = "orderStatus", target = "status")
	@Mapping(source = "expires", target = "expiresAt")
	CreateOrderResponse toResponse(OrderRecord order);
}
