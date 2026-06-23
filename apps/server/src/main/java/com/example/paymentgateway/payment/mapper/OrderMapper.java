package com.example.paymentgateway.payment.mapper;

import com.example.paymentgateway.common.mapper.BaseMapper;
import com.example.paymentgateway.payment.dto.request.CreateOrderRequest;
import com.example.paymentgateway.payment.dto.response.OrderResponse;
import com.example.paymentgateway.payment.entity.OrderRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrderMapper extends BaseMapper<OrderRecord, CreateOrderRequest, OrderResponse> {
	
	OrderRecord toEntity(CreateOrderRequest request);
	
	@Mapping(source = "orderStatus", target = "status")
	@Mapping(source = "expires", target = "expiresAt")
	OrderResponse toResponse(OrderRecord order);
	
	//PaymentResponse toPaymentResponse(Pay);
}
