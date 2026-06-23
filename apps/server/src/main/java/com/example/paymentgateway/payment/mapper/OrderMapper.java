package com.example.paymentgateway.payment.mapper;

import com.example.paymentgateway.payment.dto.request.CreateOrderRequest;
import com.example.paymentgateway.payment.dto.response.OrderResponse;
import com.example.paymentgateway.payment.dto.response.PaymentResponse;
import com.example.paymentgateway.payment.entity.OrderRecord;
import com.example.paymentgateway.payment.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
	
	OrderRecord toEntity(CreateOrderRequest request);
	
	@Mapping(source = "orderStatus", target = "status")
	@Mapping(source = "expires", target = "expiresAt")
	OrderResponse toResponse(OrderRecord order);
	
	@Mapping(target = "orderId", source = "order.id")
	@Mapping(target = "paymentStatus", source = "status")
	PaymentResponse toPaymentResponse(Payment payment);
	
	@Mapping(target = "orderId", source = "order.id")
	@Mapping(target = "paymentStatus", source = "status")
	List<PaymentResponse> toPaymentResponseList(List<Payment> payment);
}
