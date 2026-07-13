package com.example.paymentgateway.payment.mapper;

import com.example.paymentgateway.payment.dto.response.PaymentResponse;
import com.example.paymentgateway.payment.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {
	
	@Mapping(target = "paymentStatus", source = "status")
	PaymentResponse toResponse(Payment payment);
	
	List<PaymentResponse> toResponse(List<Payment> payment);
}
